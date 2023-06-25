import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, catchError, map } from 'rxjs';
import { Order } from '../models/order';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseOrderURL = 'http://localhost:9000/order'
  constructor(private errorHandling: ErrorHandlingService, private http: HttpClient) { }

  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  getOrderFromUser(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseOrderURL}/user/${userId}`, this.options).pipe(
      catchError(this.errorHandling.handleError<Order[]>('getOrderFromUser', []))
    )
  }

  createOrder(userId: number): Observable<Order> {
    return this.http.post<Order>(this.baseOrderURL, { customerId: userId }, this.options).pipe(
      catchError(this.errorHandling.handleError<Order>('createOrder', {} as Order))
    ) as Observable<Order>;
  }

  deleteOrder(orderId: number): Observable<any> {
    return this.http.delete(`${this.baseOrderURL}/${orderId}`, this.options).pipe(
      catchError(this.errorHandling.handleError<any>('deleteOrder', {}))
    );
  }

  finishOrder(orderId: number): Observable<Order> {
    return this.http.put<Order>(`${this.baseOrderURL}/${orderId}/close`, {} as Order, this.options).pipe(
      catchError(this.errorHandling.handleError<Order>('finishOrder', {} as Order))
    );
  }
}
