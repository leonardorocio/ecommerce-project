import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Shipper } from '../models/shipper';

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
      'Content-Type': 'application/json'
    })
  }

  getShippers(): Observable<Shipper[]> {
    return this.http.get<Shipper[]>(this.baseShipperURL, this.options).pipe(
      catchError(this.errorHandling.handleError<Shipper[]>('getShippers', [] as Shipper[]))
    )
  }
}
