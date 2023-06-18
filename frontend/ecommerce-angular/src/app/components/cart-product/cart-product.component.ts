import { Component, Input, OnChanges, OnInit, Output } from '@angular/core';
import { Order, OrderDetails } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { EventEmitter } from '@angular/core';
import Swal, { SweetAlertOptions } from 'sweetalert2';
import { CartService } from 'src/app/services/cart.service';
import { Cart } from 'src/app/models/cart';

@Component({
  selector: 'app-cart-product',
  templateUrl: './cart-product.component.html',
  styleUrls: ['./cart-product.component.css'],
})
export class CartProductComponent {
  constructor(
    private cartService: CartService
  ) {}

  @Input() cart!: Cart;
  @Input() user!: User;
  @Output() cartUpdate = new EventEmitter<Cart>();

  updateQuantity(orderDetails: OrderDetails, update: number, index: number) {
    this.cartService.updateQuantity(this.cart, orderDetails, update, index).subscribe((cart) => {
      this.cart = cart;
    })
  }

  async clearCart(order: Order) {
    this.cart = await this.cartService.clearCart(this.cart, order);
    this.cartUpdate.emit(this.cart);
  }

  removeProduct(orderDetails: OrderDetails) {
    this.cartService.removeProduct(this.cart, orderDetails).subscribe((cart) => {
      this.cart = cart;
    })
  }
}
