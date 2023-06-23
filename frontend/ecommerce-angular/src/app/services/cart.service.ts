import { Injectable } from '@angular/core';
import { OrderService } from './order.service';
import { OrderDetailsService } from './order-details.service';
import { ToastrService } from 'ngx-toastr';
import { User } from '../models/user';
import { Cart } from '../models/cart';
import { Order, OrderDetails } from '../models/order';
import { EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import Swal, { SweetAlertOptions } from 'sweetalert2';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor(
    private orderService: OrderService,
    private orderDetailsService: OrderDetailsService
  ) {}

  fetchCart(user: User): Observable<Cart> {
    return new Observable<Cart>((observer) => {
      const openOrder: Order = user.userOrders.filter((order) => !order.closed)[0];
      if (openOrder != null && openOrder != undefined) {
        const cart: Cart = this.initializeCart(openOrder);
        observer.next(cart);
        observer.complete();
      } else {
        this.createOrder(user).subscribe((order) => {
          observer.next(this.initializeCart(order));
          observer.complete();
        })
      }
    })
  }

  initializeCart(order: Order): Cart {
    let cart: Cart = {
      items: [],
      order: {} as Order
    };
    if (order != null && order != undefined) {
      cart.items = order.orderDetailsId;
      cart.order = order;
    }
    this.updateLocalCart(cart);
    return cart;
  }

  updateLocalCart(cart: Cart) {
    const user: User = JSON.parse(localStorage['user']);
    if (cart.order == null && cart.order == undefined) {
      user.userOrders.shift();
    } else {
      const orderIndex = user.userOrders.findIndex((o) => o.orderId === cart.order.orderId);
      if (orderIndex != -1) {
        user.userOrders[orderIndex] = cart.order;
      } else {
        user.userOrders.push(cart.order);
      }
    }
    localStorage['user'] = JSON.stringify(user);
  }

  createOrder(user: User): Observable<Order> {
    return new Observable<Order>((observer) => {
      this.orderService.createOrder(user.userId).subscribe((order) => {
        observer.next(order);
        observer.complete();
      });
    });
  }

  updateQuantity(
    cart: Cart,
    orderDetails: OrderDetails,
    update: number,
    index: number
  ): Observable<Cart> {
    return new Observable<Cart>((observer) => {
      orderDetails.order = cart.order;
      this.orderDetailsService
        .updateOrderDetails(orderDetails, update)
        .subscribe((orderDetails) => {
          cart.items[index] = orderDetails;
          this.updateLocalCart(cart);
          observer.next(cart);
          observer.complete();
        });
    });
  }

  async clearCart(cart: Cart, order: Order): Promise<Cart> {
    const result = await Swal.fire({
      title: 'Deseja apagar todos os produtos do carrinho?',
      text: 'Essa ação é irreversível!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#ff0000',
      confirmButtonText: 'Confirmar',
    } as SweetAlertOptions);
    if (result.value) {
      cart = {} as Cart;
      this.orderService.deleteOrder(order.orderId).subscribe(() => {
        this.updateLocalCart(cart);
        history.replaceState({}, '');
        window.location.reload();
      });
    }
    return cart;
  }

  removeProduct(cart: Cart, orderDetails: OrderDetails): Observable<Cart> {
    return new Observable<Cart>((observer) => {
      if (cart.items.length == 1) {
        this.clearCart(cart, cart.order);
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
                cart.items = cart.items.filter((o) => o !== orderDetails);
                cart.order.orderDetailsId = cart.items;
                this.updateLocalCart(cart);
                observer.next(cart);
                observer.complete();
              });
          }
        });
      }
    });
  }

  addProductToCart(cart: Cart): Observable<Cart> {
    return new Observable<Cart>((observer) => {
      let { navigationId: _, ...product } = history.state;
      let orderDetailsFiltered = cart.items.filter(
        (item) => JSON.stringify(item.product) === JSON.stringify(product)
      )[0];
      if (!cart.items.includes(orderDetailsFiltered)) {
        let body = {
          orderId: cart.order.orderId,
          productId: (product as Product).productId,
          quantity: 1
        }
        this.orderDetailsService
          .createOrderDetails(body)
          .subscribe((orderDetails) => {
            cart.items.push(orderDetails);
            this.updateLocalCart(cart);
            observer.next(cart);
            observer.complete();
          });
      } else {
        this.updateQuantity(
          cart,
          orderDetailsFiltered,
          1,
          cart.items.indexOf(orderDetailsFiltered)
        ).subscribe((cart) => {
          observer.next(cart);
          observer.complete();
        });
      }
    });
  }
}
