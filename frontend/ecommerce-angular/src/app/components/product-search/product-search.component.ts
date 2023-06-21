import { Component, DoCheck, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  Observable,
  defaultIfEmpty,
  distinctUntilChanged,
  isEmpty,
  switchMap,
  tap,
} from 'rxjs';
import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css'],
})
export class ProductSearchComponent implements OnInit {
  categories!: Category[];
  searchedProducts$!: Observable<Product[]>;
  showingItems!: Product[];
  isEmpty!: boolean;
  filter: string = '';

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe((categories) => {
      this.categories = categories;
    });
    this.searchedProducts$ = this.productService.searchTerms.pipe(
      distinctUntilChanged(),
      switchMap((term) => this.productService.searchProduct(term))
    );
    this.searchedProducts$.subscribe((products) => {
      this.showingItems = products;
      this.isEmpty = products.length == 0;
    });
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  selectFilter(name: string) {
    this.filter = name;
    this.productService.nextTerm(name);
  }
}
