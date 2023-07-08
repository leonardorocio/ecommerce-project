import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Category } from '../models/category';
import { Observable, catchError, share, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  categoryURL = 'http://localhost:9000/category';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private errorHandling: ErrorHandlingService, private http: HttpClient) {}

  getProductCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoryURL, this.httpOptions).pipe(
      catchError(this.errorHandling.handleError<Category[]>('getCategories', []))
    );
  }

  postProductCategory(requestBody: {}): Observable<Category> {
    return this.http.post<Category>(this.categoryURL, requestBody, this.httpOptions).pipe(
      catchError(this.errorHandling.handleError<Category>('postProductCategory', {} as Category))
    )
  }

  updateProductCategory(requestBody: {}, id: number) {
    return this.http.put<Category>(`${this.categoryURL}/${id}`, requestBody, this.httpOptions).pipe(
      catchError(this.errorHandling.handleError<Category>('updateProductCategory', {} as Category))
    )
  }

  deleteProductCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.categoryURL}/${id}`, this.httpOptions).pipe(
      catchError(this.errorHandling.handleError<void>('postProductCategory'))
    )
  }



}
