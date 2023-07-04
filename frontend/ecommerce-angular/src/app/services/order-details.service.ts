import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, catchError } from 'rxjs';
import { Order, OrderDetails } from '../models/order';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class OrderDetailsService {

  baseOrderDetailsURL = 'http://localhost:9000/details'

  constructor(private errorHandling: ErrorHandlingService, private http: HttpClient) { }

  options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  updateOrderDetails(id: number, body: any): Observable<OrderDetails> {

    return this.http.put<OrderDetails>(`${this.baseOrderDetailsURL}/${id}`, body, this.options).pipe(
      catchError(this.errorHandling.handleError<OrderDetails>('updateOrderDetails', {} as OrderDetails))
    ) as Observable<OrderDetails>;
  }

  createOrderDetails(body: any): Observable<OrderDetails> {
    return this.http.post<OrderDetails>(this.baseOrderDetailsURL, body, this.options).pipe(
      catchError(this.errorHandling.handleError<OrderDetails>('createOrderDetails', {} as OrderDetails))
    );
  }

  deleteOrderDetails(orderDetailsId: number) {
    return this.http.delete(`${this.baseOrderDetailsURL}/${orderDetailsId}`, this.options).pipe(
      catchError(this.errorHandling.handleError('deleteOrderDetails', {}))
    );
  }
}
