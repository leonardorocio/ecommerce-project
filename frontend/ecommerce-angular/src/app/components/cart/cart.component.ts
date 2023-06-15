import {
  Component,
  DoCheck,
  IterableDiffers,
  KeyValueDiffers,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { Order, OrderDetails } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { Shipment } from 'src/app/models/shipment';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CartService } from 'src/app/services/cart.service';
import { Cart } from 'src/app/models/cart';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  constructor(
    private router: Router,
    private toastr: ToastrService,
    private cartService: CartService
  ) {}

  user: User = JSON.parse(localStorage['user']);
  cart!: Cart;
  shipment!: Shipment;

  ngOnInit(): void {
    this.cart = this.cartService.initializeCart(this.user);
    if (history.state['productId']) {
      this.cartService.addProductToCart(this.cart).subscribe((cart) => {
        this.cart = cart;
        history.replaceState({}, '');
      });
    }
  }

  cartUpdate(cart: Cart) {
    this.cart = cart;
  }

  selectShipment(shipment: Shipment) {
    this.shipment = shipment;
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  finishOrder() {
    this.toastr
      .success('Voltando ao início...', 'Compra finalizada com sucesso!')
      .onShown.subscribe(() => this.goToDashboard());
  }
}
