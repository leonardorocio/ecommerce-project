import { Component, DoCheck, OnInit } from '@angular/core';

import { UserService } from './services/user.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements DoCheck {
  constructor(private cookieService: CookieService) {}

  title = 'ecommerce-angular';
  userId!: number;

  ngDoCheck(): void {
    const sessionStorageData = this.cookieService.check('user')
      ? JSON.parse(this.cookieService.get('user')).userId
      : -1;
    if (sessionStorageData !== this.userId) {
      this.userId = sessionStorageData;
    }
  }
}
