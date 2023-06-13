import { Component, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Order, OrderDetails } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { OrderDetailsService } from 'src/app/services/order-details.service';
import { OrderService } from 'src/app/services/order.service';
import { EventEmitter } from '@angular/core';
import Swal, { SweetAlertOptions } from 'sweetalert2';

@Component({
  selector: 'app-cart-product',
  templateUrl: './cart-product.component.html',
  styleUrls: ['./cart-product.component.css'],
})
export class CartProductComponent implements OnInit {
  constructor(
    private orderService: OrderService,
    private orderDetailsService: OrderDetailsService
  ) {}

  order!: Order;
  items!: OrderDetails[];
  @Input() user!: User;
  @Output() cartOrder = new EventEmitter<Order>();

  ngOnInit(): void {
    this.orderService.getOrderFromUser(this.user.userId).subscribe((orders) => {
      this.order = orders.filter((o) => !o.closed)[0];
      if (!this.order) {
        this.orderService.createOrder(this.user.userId).subscribe((order) => {
          this.order = order;
        });
      }
      this.items = this.order.orderDetailsId;
      if ('productId' in history.state) {
        this.addProductToCart();
        history.replaceState({}, '');
      }
      this.cartOrder.emit(this.order);
    });
  }

  updateQuantity(orderDetails: OrderDetails, update: number, index: number) {
    orderDetails.order = this.order;
    this.orderDetailsService
      .updateOrderDetails(orderDetails, update)
      .subscribe((orderDetails) => {
        this.items[index] = orderDetails;
      });
  }

  clearCart(order: Order) {
    Swal.fire({
      title: 'Deseja apagar todos os produtos do carrinho?',
      text: 'Essa ação é irreversível!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff0000',
      confirmButtonText: 'Confirmar',
    } as SweetAlertOptions).then((result) => {
      if (result.value) {
        this.orderService.deleteOrder(order.orderId).subscribe(() => {
          history.replaceState({}, '');
          window.location.reload();
        });
      }
    });
  }

  removeProduct(orderDetails: OrderDetails) {
    if (this.items.length == 1) {
      this.clearCart(this.order);
    } else {
      Swal.fire({
        title: 'Deseja apagar esse produto do carrinho?',
        text: 'Essa ação é irreversível!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ff0000',
        confirmButtonText: 'Confirmar',
      } as SweetAlertOptions).then((result) => {
        if (result.value) {
          this.orderDetailsService
            .deleteOrderDetails(orderDetails.orderDetailsId)
            .subscribe(() => {
              this.items = this.items.filter((o) => o !== orderDetails);
              window.location.reload();
            });
        }
      });
    }
  }

  addProductToCart() {
    let { navigationId: _, ...product } = history.state;
    let orderDetailsFiltered = this.items.filter(
      (item) => JSON.stringify(item.product) === JSON.stringify(product)
    )[0];
    if (!this.items.includes(orderDetailsFiltered)) {
      this.orderDetailsService
        .createOrderDetails(this.order, history.state, 1)
        .subscribe((orderDetails) => this.items.push(orderDetails));
    } else {
      this.updateQuantity(
        orderDetailsFiltered,
        1,
        this.items.indexOf(orderDetailsFiltered)
      );
    }
  }
}
