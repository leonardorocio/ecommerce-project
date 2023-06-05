import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Category } from '../models/category';
import { Observable, catchError } from 'rxjs';

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

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoryURL, this.httpOptions).pipe(
      catchError(this.errorHandling.handleError('getCategories', []))
    );
  }

}
