import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { Product } from '../models/product';
import { Comment } from '../models/comment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject, catchError, tap } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  searchTerms = new BehaviorSubject<string>('');

  private productsURL: string = 'http://localhost:9000/products';
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
    params: new HttpParams({
      fromString: '',
    }),
  };

  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}

  nextTerm(term: string) {
    this.searchTerms.next(term);
  }

  getProducts(): Observable<Product[]> {
    return this.http
      .get<Product[]>(this.productsURL, this.httpOptions)
      .pipe(
        catchError(this.errorHandling.handleError<Product[]>('getProducts', []))
      );
  }

  getProductsSortedByDiscount(): Observable<Product[]> {
    return this.http
      .get<Product[]>(`${this.productsURL}/discounted`, this.httpOptions)
      .pipe(
        catchError(
          this.errorHandling.handleError<Product[]>(
            'getProductsSortedByDiscount',
            []
          )
        )
      );
  }

  createProduct(requestBody: any) {
    return this.http
      .post<Product>(this.productsURL, requestBody, this.httpOptions)
      .pipe(
        catchError(
          this.errorHandling.handleError<Product>(
            'createProduct',
            {} as Product
          )
        )
      );
  }

  getProductById(productId: number): Observable<Product> {
    return this.http
      .get<Product>(`${this.productsURL}/${productId}`, this.httpOptions)
      .pipe(
        catchError(
          this.errorHandling.handleError<Product>(
            'getProductById',
            {} as Product
          )
        )
      );
  }

  updateProduct(requestBody: any, id: number): Observable<Product> {
    return this.http
      .put<Product>(`${this.productsURL}/${id}`, requestBody, this.httpOptions)
      .pipe(
        catchError(
          this.errorHandling.handleError<Product>(
            'updateProduct',
            {} as Product
          )
        )
      );
  }

  deleteProduct(id: number) {
    return this.http
      .delete<void>(`${this.productsURL}/${id}`, this.httpOptions)
      .pipe(catchError(this.errorHandling.handleError<void>('deleteProduct')));
  }

  // filterProductByCategory(requestBody: any) {
  //   return this.http.get<Product[]>(`${this.productsURL}/filter`, requestBody)
  // }

  searchProduct(term: string): Observable<Product[]> {
    this.httpOptions.params = this.httpOptions.params.set('name', term);
    return this.http
      .get<Product[]>(`${this.productsURL}/search`, this.httpOptions)
      .pipe(
        catchError(
          this.errorHandling.handleError<Product[]>('searchProduct', [])
        )
      );
  }

  getCommentsByProduct(id: number): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.productsURL}/${id}/comments`, this.httpOptions)
      .pipe(
        catchError(
          this.errorHandling.handleError<Comment[]>(
            'getCommentsFromProduct',
            []
          )
        )
      );
  }
}
