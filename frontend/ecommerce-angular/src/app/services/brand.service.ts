import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Brand } from '../models/brand';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  constructor(private errorHandling: ErrorHandlingService, private http: HttpClient) { }

  private brandBaseURL = "http://localhost:9000/brands";
  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  getBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>(this.brandBaseURL, this.options).pipe(
      catchError(this.errorHandling.handleError<Brand[]>('getBrands', []))
    );
  }

  getBrandProducts(id: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.brandBaseURL}/${id}/products`, this.options).pipe(
      catchError(this.errorHandling.handleError<Product[]>('getBrandProducts', []))
    );
  }

  createBrand(requestBody: any): Observable<Brand> {
    return this.http.post<Brand>(this.brandBaseURL, requestBody, this.options).pipe(
      catchError(this.errorHandling.handleError<Brand>('createBrand', {} as Brand))
    );
  }

  updateBrand(requestBody: any, id: number) {
    return this.http.put<Brand>(`${this.brandBaseURL}/${id}`, requestBody, this.options).pipe(
      catchError(this.errorHandling.handleError<Brand>('updateBrand', {} as Brand))
    );
  }

  deleteBrand(id: number): Observable<void> {
    return this.http.delete<void>(`${this.brandBaseURL}/${id}`, this.options).pipe(
      catchError(this.errorHandling.handleError<void>('deleteBrand'))
    );
  }
}
