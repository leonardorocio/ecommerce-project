import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Shipper } from '../models/shipper';
import { Shipment } from '../models/shipment';

@Injectable({
  providedIn: 'root',
})
export class ShipperService {
  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}

  baseShipperURL = 'http://localhost:9000/shipper';
  options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  getShippers(): Observable<Shipper[]> {
    return this.http
      .get<Shipper[]>(this.baseShipperURL, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<Shipper[]>(
            'getShippers',
            [] as Shipper[]
          )
        )
      );
  }

  getShipperById(id: number): Observable<Shipper> {
    return this.http.get<Shipper>(`${this.baseShipperURL}/${id}`, this.options).pipe(
      catchError(this.errorHandling.handleError<Shipper>('getShipperById', {} as Shipper))
    )
  }

  deleteShipper(id: number): Observable<void> {
    return this.http.get<void>(`${this.baseShipperURL}/${id}`, this.options).pipe(
      catchError(this.errorHandling.handleError<void>('deleteShipper'))
    )
  }

  postShipper(requestBody: any) {
    return this.http.post<Shipper>(this.baseShipperURL, requestBody, this.options).pipe(
      catchError(this.errorHandling.handleError<Shipper>('postShipper', {} as Shipper))
    )
  }

  updateShipper(requestBody: any, id: number): Observable<Shipper> {
    return this.http.put<Shipper>(`${this.baseShipperURL}/${id}`, requestBody, this.options).pipe(
      catchError(this.errorHandling.handleError<Shipper>('updateShipper', {} as Shipper))
    )
  }

  getShippersOnGoingShipments(id: number): Observable<Shipment[]> {
    return this.http
      .get<Shipment[]>(`${this.baseShipperURL}/${id}/open `, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<Shipment[]>('getShippers', [])
        )
      );
  }
}
