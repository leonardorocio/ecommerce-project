import { Component, DoCheck, OnInit } from '@angular/core';

import { UserService } from './services/user.service';

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
    const sessionStorageData: number =
      sessionStorage['user'] !== undefined
        ? Number.parseInt(
            JSON.parse(sessionStorage.getItem('user') ?? '').userId
          )
        : -1;
    if (sessionStorageData !== this.userId) {
      this.userId = sessionStorageData;
    }
  }
}
