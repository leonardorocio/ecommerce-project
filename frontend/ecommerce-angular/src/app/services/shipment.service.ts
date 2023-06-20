import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Address } from '../models/address';
import { Shipment } from '../models/shipment';
import { Shipper } from '../models/shipper';
import { Order } from '../models/order';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ShipmentService {
  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient,
  ) {}

  private baseShipmentURL = 'http://localhost:9000/shipment';
  private correiosAPIURL = 'https://h-apigateway.conectagov.estaleiro.serpro.gov.br/api-cep/v1/consulta/cep/';
  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  consultZipCode(address: Address): Observable<any> {
    return this.http.get(`${this.correiosAPIURL}/${address.zipCode}`, this.options).pipe(
      catchError(this.errorHandling.handleError<any>('consultZipCode', ''))
    )
  }

  generateShipmentOption(order: Order, shipper: Shipper): Shipment {
    const date = new Date();
    date.setDate(date.getDate() + (7 - shipper.fixedTax / 10));
    // this.consultZipCode(address).subscribe((result) => console.log(result));
    return {
      shippingPrice: Number.parseFloat((shipper.fixedTax + (Math.random() * 100) % 50).toPrecision(4)),
      expectedDeliveryDate: date.toISOString().split('T')[0],
      shipper: shipper,
      order: order
    } as Shipment;
  }


}
