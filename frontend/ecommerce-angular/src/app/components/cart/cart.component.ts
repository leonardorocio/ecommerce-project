import { Component, DoCheck, OnInit, SimpleChanges } from '@angular/core';
import { User } from 'src/app/models/user';
import { Shipment } from 'src/app/models/shipment';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CartService } from 'src/app/services/cart.service';
import { Cart } from 'src/app/models/cart';
import { OrderService } from 'src/app/services/order.service';
import Swal, { SweetAlertOptions } from 'sweetalert2';
import { AlertService } from 'src/app/services/alert.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit, DoCheck {
  constructor(
    private router: Router,
    private alert: AlertService,
    private cartService: CartService,
    private cookieService: CookieService
  ) {}

  user!: User;
  hasProducts = false;
  cart!: Cart;
  shipment!: Shipment;

  ngOnInit(): void {
    if (this.cookieService.check("user")) {
      this.user = JSON.parse(this.cookieService.get("user"));
      this.cartService.fetchCart(this.user).subscribe((fetchedCart) => {
        this.cart = fetchedCart;
        this.hasProducts = this.cart.order.orderDetailsList.length > 0;
        if (history.state['id']) {
          this.cartService
            .addProductToCart(this.cart)
            .subscribe((updatedCart) => {
              this.cart = updatedCart;
              history.replaceState({}, '');
            });
        }
      });
    }
  }

  ngDoCheck(): void {
    var changes!: boolean;
    if (this.cart.order.orderDetailsList != undefined) {
      changes = this.cart.order.orderDetailsList.length > 0;
    }
    if (changes != this.hasProducts) {
      this.hasProducts = changes;
    }
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  cartUpdate(cart: Cart) {
    this.cart = cart;
  }

  selectShipment(shipment: Shipment) {
    this.shipment = shipment;
  }
}
