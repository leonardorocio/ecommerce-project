import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Order } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { AlertService } from 'src/app/services/alert.service';
import { CartService } from 'src/app/services/cart.service';
import { UserService } from 'src/app/services/user.service';
import Swal, { SweetAlertOptions } from 'sweetalert2';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  user!: User;
  role!: string;
  name!: string;
  email!: string;
  birthDate!: string;
  userOrders!: Order[];
  selected!: number;

  ngOnInit(): void {
    if (this.cookieService.check("user")) {
      this.user = JSON.parse(this.cookieService.get("user"));
      ({
        name: this.name,
        email: this.email,
        birthDate: this.birthDate,
        role: this.role,
        userOrders: this.userOrders,
      } = this.user);
      this.role = this.role.replace('ROLE_', '');
    }
  }

  constructor(
    public cartService: CartService,
    private userService: UserService,
    private alert: AlertService,
    private router: Router,
    private cookieService: CookieService
  ) {}

  selectOrderToShow(selected: number) {
    if (this.selected !== selected) {
      this.selected = selected;
    } else {
      this.selected = -1;
    }
  }

  async updateUser() {
    const result = await this.alert.question(
      'Deseja alterar suas informações?'
    );
    const requestBody = { name: this.name, birthDate: this.birthDate };
    if (result) {
      this.userService
        .updateUser(requestBody, this.user.id)
        .subscribe((user) => {
          this.user = user;
          this.cookieService.set("user", JSON.stringify(this.user));
          // sessionStorage['user'] = JSON.stringify(this.user);
        });
    }
  }

  goToAdmin() {
    this.router.navigate(['/admin'], { state: { userId: this.user.id } });
  }
}
