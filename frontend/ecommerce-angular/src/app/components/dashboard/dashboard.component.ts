import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product';
import { AuthService } from 'src/app/services/auth.service';
import { CategoryService } from 'src/app/services/category.service';
import { Category } from 'src/app/models/category';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];
  private user: User = JSON.parse(localStorage['user']);

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productService
      .getProducts()
      .subscribe((products) => (this.products = products.slice(0, 5)));

    this.categoryService
      .getCategories()
      .subscribe((categories) => (this.categories = categories));
  }

  goToSearchProducts(term: string) {
    this.productService.nextTerm(term);
    this.router.navigate(['/search'], { queryParams: { name: term } });
  }

}
