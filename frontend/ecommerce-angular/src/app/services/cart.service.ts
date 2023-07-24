import { Injectable } from '@angular/core';
import { OrderService } from './order.service';
import { OrderDetailsService } from './order-details.service';
import { User } from '../models/user';
import { Cart } from '../models/cart';
import { Order, OrderDetails } from '../models/order';
import { Observable, of, switchMap, tap } from 'rxjs';
import { Product } from '../models/product';
import { AlertService } from './alert.service';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor(
    private orderService: OrderService,
    private orderDetailsService: OrderDetailsService,
    private alert: AlertService,
    private cookieService: CookieService,
    private userService: UserService
  ) {}

  fetchCart(user: User): Observable<Order> {
    return this.userService.getUsersOrders(user.id).pipe(
      switchMap((orders) => {
        const openOrder: Order | undefined = orders.find(order => !order.closed);
        return openOrder !== undefined
          ? of(openOrder)
          : this.orderService.postOrder(user.id);
      })
    );
  }

  addProductToOrder(order: Order): Observable<Order> {
    const product: Product = JSON.parse(this.cookieService.get('product'));
    const orderDetailsFiltered: OrderDetails | undefined =
      order.orderDetailsList.find((item) => item.product.id === product.id);
    const body = {
      orderId: order.id,
      productId: (product as Product).id,
      quantity: 1,
    };
    if (orderDetailsFiltered === undefined) {
      return this.orderDetailsService.postOrderDetails(body).pipe(
        tap((orderDetails) => order.orderDetailsList.push(orderDetails)),
        switchMap((orderDetails) => of(order))
      );
    } else {
      return this.updateQuantity(
        order,
        orderDetailsFiltered,
        1,
        order.orderDetailsList.indexOf(orderDetailsFiltered)
      );
    }
  }

  updateQuantity(
    order: Order,
    orderDetails: OrderDetails,
    update: number,
    index: number
  ): Observable<Order> {
    return new Observable<Order>((observer) => {
      const body = {
        orderId: order.id,
        productId: orderDetails.product.id,
        quantity: orderDetails.quantity + update,
      };
      this.orderDetailsService
        .updateOrderDetails(body, orderDetails.id)
        .subscribe((orderDetails) => {
          order.orderDetailsList[index] = orderDetails;
          observer.next(order);
          observer.complete();
        });
    });
  }

  async clearCart(order: Order): Promise<Order> {
    const result = await this.alert.warning(
      'Deseja apagar todos os produtos do carrinho',
      'Essa ação é irreversível'
    );
    if (result) {
      this.orderService.deleteOrder(order.id).subscribe(() => {
        history.replaceState({}, '');
        window.location.reload();
      });
    }
    return {} as Order;
  }

  removeProduct(order: Order, orderDetails: OrderDetails): Observable<Order> {
    return new Observable<Order>((observer) => {
      if (order.orderDetailsList.length == 1) {
        this.clearCart(order);
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
                  order.orderDetailsList = order.orderDetailsList.filter(
                    (o) => o !== orderDetails
                  );
                  observer.next(order);
                  observer.complete();
                });
            }
          });
      }
    });
  }
}
