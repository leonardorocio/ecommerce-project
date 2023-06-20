import { Component, Input, OnChanges, OnInit, Output } from '@angular/core';
import { Order, OrderDetails } from 'src/app/models/order';
import { User } from 'src/app/models/user';
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

  updateQuantity(orderDetails: OrderDetails, update: number, index: number) {
    this.cartService.updateQuantity(this.cart, orderDetails, update, index).subscribe((cart) => {
      this.cart = cart;
    })
  }

  async clearCart(order: Order) {
    this.cart = await this.cartService.clearCart(this.cart, order);
  }

  removeProduct(orderDetails: OrderDetails) {
    this.cartService.removeProduct(this.cart, orderDetails).subscribe((cart) => {
      this.cart = cart;
    })
  }
}
