import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product';
import { AuthService } from 'src/app/services/auth.service';
import { CategoryService } from 'src/app/services/category.service';
import { Category } from 'src/app/models/category';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { Favorite } from 'src/app/models/favorite';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];
  favorites: Favorite[] = [];

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private userService: UserService,
    private router: Router,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.productService
      .getProducts()
      .subscribe((products) => (this.products = products.slice(0, 5)));

    this.categoryService
      .getProductCategories()
      .subscribe((categories) => (this.categories = categories));

    if (this.cookieService.check('user')) {
      this.userService
        .getUsersFavorites(JSON.parse(this.cookieService.get('user')).id)
        .subscribe((favorites) => {
          this.favorites = favorites;
          console.log(this.favorites);
        });
    }
  }

  isFavorite(product: Product) {
    const favorite = this.favorites.filter((favorite) => favorite.product.id === product.id)[0];
    return favorite;
  }

  goToSearchProducts(term: string) {
    this.productService.nextTerm(term);
    this.router.navigate(['/search'], { queryParams: { name: term } });
  }
}
