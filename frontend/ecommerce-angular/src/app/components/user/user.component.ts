import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Order } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { AlertService } from 'src/app/services/alert.service';
import { CartService } from 'src/app/services/cart.service';
import { DropdownService } from 'src/app/services/dropdown.service';
import { UserService } from 'src/app/services/user.service';
import Swal, { SweetAlertOptions } from 'sweetalert2';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  user: User = JSON.parse(localStorage['user']);
  role!: string;
  name!: string;
  email!: string;
  birthDate!: string;
  userOrders!: Order[];
  selected!: number;

  constructor(
    public dropDownService: DropdownService,
    public cartService: CartService,
    private userService: UserService,
    private alert: AlertService,
    private router: Router
  ) {
    ({
      name: this.name,
      email: this.email,
      birthDate: this.birthDate,
      role: this.role,
      userOrders: this.userOrders,
    } = this.user);
    this.role = this.role.replace('ROLE_', '');
  }

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
    const requestBody = {name: this.name, birthDate: this.birthDate}
    if (result) {
      this.userService
        .updateUser(this.user.userId, requestBody)
        .subscribe((user) => {
          this.user = user;
          localStorage['user'] = JSON.stringify(this.user);
        });
    }
  }

  goToAdmin() {
    this.router.navigate(['/admin'], {state: { userId: this.user.userId}});
  }
}
