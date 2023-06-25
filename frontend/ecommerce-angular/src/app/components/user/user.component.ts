import { Component } from '@angular/core';
import { Order } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { CartService } from 'src/app/services/cart.service';
import { DropdownService } from 'src/app/services/dropdown.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {

  user: User = JSON.parse(localStorage['user']);
  role!: string
  name!: string;
  email!: string;
  birthDate!: string;
  userOrders!: Order[];
  selected!: number;

  constructor(public dropDownService: DropdownService, public cartService: CartService) {
    ({name: this.name, email: this.email, birthDate: this.birthDate, role: this.role, userOrders: this.userOrders} = this.user);
    this.role = this.role.replace("ROLE_", "");
  }

  selectOrderToShow(selected: number) {
    if (this.selected !== selected) {
      this.selected = selected;
    } else {
      this.selected = -1;
    }
  }

}
