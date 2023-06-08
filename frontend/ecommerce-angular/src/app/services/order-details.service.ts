import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, catchError } from 'rxjs';
import { OrderDetails } from '../models/order';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrderDetailsService {

  baseOrderDetailsURL = 'http://localhost:9000/orderDetails'

  constructor(private errorHandling: ErrorHandlingService, private http: HttpClient) { }

  options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  updateOrderDetails(orderDetails: OrderDetails): Observable<OrderDetails> {
    let body = {
      orderId: orderDetails.order.orderId,
      productId: orderDetails.product.productId,
      quantity: orderDetails.quantity + 1
    }
    return this.http.put<OrderDetails>(`${this.baseOrderDetailsURL}/${orderDetails.orderDetailsId}`, body, this.options).pipe(
      catchError(this.errorHandling.handleError('updateOrderDetails', orderDetails))
    ) as Observable<OrderDetails>;
  }
}
