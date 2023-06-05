import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { Product } from '../models/product';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  productsURL: string = 'http://localhost:9000/products';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}

  getProducts(): Observable<Product[]> {
    return this.http
      .get<Product[]>(this.productsURL, this.httpOptions)
      .pipe(catchError(this.errorHandling.handleError('getProducts', [])));
  }
}
