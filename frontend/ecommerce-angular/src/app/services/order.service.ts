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
      catchError(this.errorHandling.handleError('getOrderFromUser', []))
    )
  }

  createOrder(userId: number): Observable<Order> {
    return this.http.post<Order>(this.baseOrderURL, { customerId: userId }, this.options).pipe(
      catchError(this.errorHandling.handleError('createOrder', {}))
    ) as Observable<Order>;
  }

  deleteOrder(orderId: number): Observable<any> {
    return this.http.delete(`${this.baseOrderURL}/${orderId}`, this.options).pipe(
      catchError(this.errorHandling.handleError('deleteOrder', {}))
    );
  }
}
