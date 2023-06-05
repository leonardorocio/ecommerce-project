import { Component, OnInit } from '@angular/core';
import { CategoryService } from './services/category.service';
import { Category } from './models/category';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ecommerce-angular';
  categories: Category[] = [];

  constructor(public categoryService: CategoryService) {}

  ngOnInit(): void {
      this.categoryService.getCategories()
        .subscribe((categories) => this.categories = categories);
  }
}
