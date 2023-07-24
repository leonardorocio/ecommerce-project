import { Component, DoCheck, OnInit, SimpleChanges } from '@angular/core';
import { User } from 'src/app/models/user';
import { Shipment } from 'src/app/models/shipment';
import { Router } from '@angular/router';
import { CartService } from 'src/app/services/cart.service';
import { AlertService } from 'src/app/services/alert.service';
import { CookieService } from 'ngx-cookie-service';
import { Order } from 'src/app/models/order';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  constructor(
    private router: Router,
    private alert: AlertService,
    private cartService: CartService,
    private cookieService: CookieService
  ) {}

  user!: User;
  hasProducts = false;
  order!: Order;
  shipment!: Shipment;

  ngOnInit(): void {
    this.user = JSON.parse(this.cookieService.get('user'));
    this.cartService.fetchCart(this.user).subscribe((order) => {
      this.order = order;
      this.hasProducts = this.order.orderDetailsList.length > 0;
      if (this.cookieService.check('product')) {
        this.cartService.addProductToOrder(this.order).subscribe(order => {
          this.order = order;
          this.hasProducts = this.order.orderDetailsList.length > 0;
          this.cookieService.delete('product');
        })
      }
    });
    // if (this.cookieService.check('user')) {
    //   this.user = JSON.parse(this.cookieService.get('user'));
    //   this.cartService.fetchCart(this.user).subscribe((fetchedCart) => {
    //     console.log(history.state);
    //     this.cart = fetchedCart;
    //     this.hasProducts = this.cart.order.orderDetailsList.length > 0;
    //     if (history.state['id']) {
    //       this.cartService
    //         .addProductToCart(this.cart)
    //         .subscribe((updatedCart) => {
    //           this.cart = updatedCart;
    //           this.hasProducts = this.cart.order.orderDetailsList.length > 0;
    //           history.replaceState({}, '');
    //         });
    //     }
    //   });
    // }
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  selectShipment(shipment: Shipment) {
    this.shipment = shipment;
  }
}
