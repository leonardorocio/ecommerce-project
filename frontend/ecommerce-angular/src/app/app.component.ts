import { Component, DoCheck, OnInit } from '@angular/core';
import { CategoryService } from './services/category.service';
import { Category } from './models/category';
import { UserService } from './services/user.service';
import { tap, defaultIfEmpty, last, map, filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements DoCheck {
  constructor(private userService: UserService) {}

  title = 'ecommerce-angular';
  userId!: number;

  ngDoCheck(): void {
    if (localStorage['user'] !== undefined) {
      const localStorageData: number = Number.parseInt(
        JSON.parse(localStorage['user']).userId
      );
      if (localStorageData !== this.userId) {
        this.userId = localStorageData;
      }
    }
  }
}
