import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, catchError } from 'rxjs';
import { Order, OrderDetails } from '../models/order';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class OrderDetailsService {
  baseOrderDetailsURL = 'http://localhost:9000/details';

  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}

  options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  getOrderDetails(): Observable<OrderDetails[]> {
    return this.http
      .get<OrderDetails[]>(this.baseOrderDetailsURL, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<OrderDetails[]>('getOrderDetails', [])
        )
      );
  }

  getOrderDetailsById(id: number): Observable<OrderDetails> {
    return this.http
      .get<OrderDetails>(`${this.baseOrderDetailsURL}/${id}`, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<OrderDetails>(
            'getOrderDetails',
            {} as OrderDetails
          )
        )
      );
  }

  updateOrderDetails(body: any, id: number): Observable<OrderDetails> {
    return this.http
      .put<OrderDetails>(
        `${this.baseOrderDetailsURL}/${id}`,
        body,
        this.options
      )
      .pipe(
        catchError(
          this.errorHandling.handleError<OrderDetails>(
            'updateOrderDetails',
            {} as OrderDetails
          )
        )
      ) as Observable<OrderDetails>;
  }

  postOrderDetails(body: any): Observable<OrderDetails> {
    return this.http
      .post<OrderDetails>(this.baseOrderDetailsURL, body, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<OrderDetails>(
            'createOrderDetails',
            {} as OrderDetails
          )
        )
      );
  }

  deleteOrderDetails(id: number) {
    return this.http
      .delete(`${this.baseOrderDetailsURL}/${id}`, this.options)
      .pipe(
        catchError(this.errorHandling.handleError('deleteOrderDetails', {}))
      );
  }
}
