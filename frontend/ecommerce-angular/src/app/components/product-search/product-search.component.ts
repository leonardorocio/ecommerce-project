import { AfterViewInit, Component, DoCheck, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  Observable,
  defaultIfEmpty,
  delay,
  distinctUntilChanged,
  isEmpty,
  map,
  of,
  switchMap,
  take,
  tap,
} from 'rxjs';
import { Brand } from 'src/app/models/brand';
import { Category } from 'src/app/models/category';
import { Product } from 'src/app/models/product';
import { BrandService } from 'src/app/services/brand.service';
import { CategoryService } from 'src/app/services/category.service';
import { PaginatorService } from 'src/app/services/paginator.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css'],
})
export class ProductSearchComponent implements OnInit {
  categories!: Category[];
  brands!: Brand[];
  items!: Product[];
  showingItems!: Product[];
  isEmpty!: boolean;
  filter: string = '';

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private brandService: BrandService,
    private router: Router,
    private paginatorService: PaginatorService
  ) {}

  ngOnInit(): void {
    this.brandService.getBrands().subscribe(brands => this.brands = brands);
    this.categoryService.getProductCategories().subscribe((categories) => {
      this.categories = categories;
    });
    this.productService.searchTerms.pipe(
      distinctUntilChanged(),
      switchMap((term) => this.productService.searchProduct(term)),
    ).subscribe((products) => {
      this.items = products
      this.isEmpty = this.items.length == 0;
    })
    this.paginatorService.updateShowingItems.pipe(
      delay(0)
    ).subscribe((showingProducts) => {
      this.showingItems = showingProducts;
    })
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  selectFilter(name: string) {
    this.filter = name;
    this.productService.nextTerm(name);
  }
}
