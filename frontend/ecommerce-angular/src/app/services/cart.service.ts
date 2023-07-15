import { Injectable } from '@angular/core';
import { OrderService } from './order.service';
import { OrderDetailsService } from './order-details.service';
import { User } from '../models/user';
import { Cart } from '../models/cart';
import { Order, OrderDetails } from '../models/order';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { AlertService } from './alert.service';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor(
    private orderService: OrderService,
    private orderDetailsService: OrderDetailsService,
    private alert: AlertService,
    private cookieService: CookieService
  ) {}

  fetchCart(user: User): Observable<Cart> {
    return new Observable<Cart>((observer) => {
      const openOrder: Order = user.userOrders.filter(
        (order) => !order.closed
      )[0];
      if (openOrder != null && openOrder != undefined && Object.keys(openOrder).length > 0) {
        const cart: Cart = this.initializeCart(openOrder);
        observer.next(cart);
        observer.complete();
      } else {
        this.createOrder(user).subscribe((order) => {
          observer.next(this.initializeCart(order));
          observer.complete();
        });
      }
    });
  }

  initializeCart(order: Order): Cart {
    let cart: Cart = {
      order: {} as Order,
    };
    if (order != null && order != undefined) {
      cart.order = order;
    }
    this.updateLocalCart(cart);
    return cart;
  }

  updateLocalCart(cart: Cart) {
    // this.cookieService.set("user", JSON.stringify(this.user));
    const user: User = JSON.parse(this.cookieService.get("user"));
    if (cart.order == null && cart.order == undefined) {
      user.userOrders.pop();
    } else {
      const orderIndex = user.userOrders.findIndex(
        (o) => o.id === cart.order.id
      );
      if (orderIndex != -1) {
        user.userOrders[orderIndex] = cart.order;
      } else {
        user.userOrders.push(cart.order);
      }
    }
    this.cookieService.set("user", JSON.stringify(user));
    // sessionStorage['user'] = JSON.stringify(user);
  }

  createOrder(user: User): Observable<Order> {
    return new Observable<Order>((observer) => {
      this.orderService.postOrder(user.id).subscribe((order) => {
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
      const body = {
        orderId: orderDetails.order.id,
        productId: orderDetails.product.id,
        quantity: orderDetails.quantity + update
      }
      this.orderDetailsService
        .updateOrderDetails(body, orderDetails.id)
        .subscribe((orderDetails) => {
          cart.order.orderDetailsList[index] = orderDetails;
          this.updateLocalCart(cart);
          observer.next(cart);
          observer.complete();
        });
    });
  }

  async clearCart(cart: Cart, order: Order): Promise<Cart> {
    const result = await this.alert.warning(
      'Deseja apagar todos os produtos do carrinho',
      'Essa ação é irreversível'
    );
    if (result) {
      cart = {} as Cart;
      this.orderService.deleteOrder(order.id).subscribe(() => {
        this.updateLocalCart(cart);
        history.replaceState({}, '');
        window.location.reload();
      });
    }
    return cart;
  }

  removeProduct(cart: Cart, orderDetails: OrderDetails): Observable<Cart> {
    return new Observable<Cart>((observer) => {
      if (cart.order.orderDetailsList.length == 1) {
        this.clearCart(cart, cart.order);
      } else {
        this.alert
          .warning(
            'Deseja apagar esse produto do carrinho?',
            'Essa ação é irreversível!'
          )
          .then((result) => {
            if (result) {
              this.orderDetailsService
                .deleteOrderDetails(orderDetails.id)
                .subscribe(() => {
                  cart.order.orderDetailsList = cart.order.orderDetailsList.filter((o) => o !== orderDetails);
                  cart.order.orderDetailsList = cart.order.orderDetailsList;
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
      let orderDetailsFiltered = cart.order.orderDetailsList.filter(
        (item) => JSON.stringify(item.product) === JSON.stringify(product)
      )[0];
      if (!cart.order.orderDetailsList.includes(orderDetailsFiltered)) {
        let body = {
          orderId: cart.order.id,
          productId: (product as Product).id,
          quantity: 1,
        };
        this.orderDetailsService
          .postOrderDetails(body)
          .subscribe((orderDetails) => {
            cart.order.orderDetailsList.push(orderDetails);
            this.updateLocalCart(cart);
            observer.next(cart);
            observer.complete();
          });
      } else {
        this.updateQuantity(
          cart,
          orderDetailsFiltered,
          1,
          cart.order.orderDetailsList.indexOf(orderDetailsFiltered)
        ).subscribe((cart) => {
          observer.next(cart);
          observer.complete();
        });
      }
    });
  }
}
