import { Component, Input, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  categories: Category[] = [];
  @Input() userId!: number;

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
      this.categoryService.getCategories()
        .subscribe((categories) => this.categories = categories);
  }
}
