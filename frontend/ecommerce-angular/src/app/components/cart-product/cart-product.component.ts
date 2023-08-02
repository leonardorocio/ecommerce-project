import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
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

  @Input() order!: Order;
  @Input() user!: User;
  @Input() enableEditing!: boolean;
  @Output() clearedCart = new EventEmitter<Order>();

  updateQuantity(orderDetails: OrderDetails, update: number, index: number) {
    this.cartService.updateQuantity(this.order, orderDetails, update, index).subscribe((order) => {
      this.order = order;
    })
  }

  async clearCart(order: Order) {
    this.order = await this.cartService.clearCart(order);
    this.clearedCart.emit(this.order);
  }

  removeProduct(orderDetails: OrderDetails) {
    if (this.order.orderDetailsList.length === 1) {
      this.clearCart(this.order);
    } else {
      this.cartService.removeProduct(this.order, orderDetails).subscribe((cart) => {
        this.order = cart;
      })
    }
  }
}
