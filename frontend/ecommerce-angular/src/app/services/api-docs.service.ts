import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, share, tap } from 'rxjs';
import { ErrorHandlingService } from './error-handling.service';
import {
  APIDocs,
  Schema,
  MethodProperties,
  SchemaProperties,
} from '../models/admin';
import { OrderDetailsService } from './order-details.service';
import { OrderService } from './order.service';
import { AddressService } from './address.service';
import { CommentService } from './comment.service';
import { ShipmentService } from './shipment.service';
import { ShipperService } from './shipper.service';
import { UserService } from './user.service';
import { CategoryService } from './category.service';
import { ProductService } from './product.service';
import { FavoriteService } from './favorite.service';
import { BrandService } from './brand.service';

@Injectable({
  providedIn: 'root',
})
export class ApiDocsService {
  APIDocsURL = 'http://localhost:9000/v3/api-docs';
  options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(
    private http: HttpClient,
    private errorHandling: ErrorHandlingService
  ) {}

  getAPIDocs(): Observable<any> {
    return this.http
      .get<any>(this.APIDocsURL, this.options)
      .pipe(catchError(this.errorHandling.handleError<any>('getAPIDocs', {})));
  }

  filterPaths(docs: APIDocs, tagName: string) {
    return Object.entries(docs.paths)
      .filter((path) => path[0].startsWith('/' + tagName))
      .map((path) => path[1]);
  }

  getRequestBodyName(method: MethodProperties): string | undefined {
    const stringyfiedRequestBody = JSON.stringify(method.requestBody) ?? '';
    return stringyfiedRequestBody ?? ''
      ? stringyfiedRequestBody.match(/"#\/components\/schemas\/(.+)"}/)?.[1]
      : '';
  }

  getRequestBody(docs: APIDocs, method: MethodProperties): SchemaProperties {
    const requestBodyName = this.getRequestBodyName(method) ?? '';
    const requestBody = Object.keys(docs.components.schemas)
      .filter((key) => key === requestBodyName)
      .map((key) => docs.components.schemas[key as keyof Schema])[0];
    return requestBodyName.length ? requestBody : ({} as SchemaProperties);
  }

  chooseServiceToCall(tagName: string): Function {
    let service: any;
    switch (tagName) {
      case 'details':
      case 'orderdetails':
        service = new OrderDetailsService(this.errorHandling, this.http);
        break;
      case 'order':
      case 'orders':
        service = new OrderService(this.errorHandling, this.http);
        break;
      case 'address':
        service = new AddressService(this.errorHandling, this.http);
        break;
      case 'comments':
        service = new CommentService(this.errorHandling, this.http);
        break;
      case 'shipments':
        service = new ShipmentService(this.errorHandling, this.http);
        break;
      case 'shipper':
      case 'shippers':
        service = new ShipperService(this.errorHandling, this.http);
        break;
      case 'users':
        service = new UserService(this.errorHandling, this.http);
        break;
      case 'category':
      case 'productcategorys':
        service = new CategoryService(this.errorHandling, this.http);
        break;
      case 'products':
        service = new ProductService(this.errorHandling, this.http);
        break;
      case 'favorites':
        service = new FavoriteService(this.errorHandling, this.http);
        break;
      case 'brands':
        service = new BrandService(this.errorHandling, this.http);
        break;
    }
    return service;
  }

  executeOperation(
    operationId: string,
    parameters: Array<any>,
    requestBody: {},
    service: Function
  ): Observable<any> {
    const objectIsEmpty = (object: {}): boolean =>
      Object.keys(object).length === 0;
    let operationReturn: Observable<any>;
    if (!objectIsEmpty(parameters) && objectIsEmpty(requestBody)) {
      operationReturn = service.constructor.prototype[operationId].call(
        service,
        ...parameters
      );
    } else if (objectIsEmpty(parameters) && !objectIsEmpty(requestBody)) {
      operationReturn = service.constructor.prototype[operationId].call(
        service,
        requestBody
      );
    } else if (!objectIsEmpty(parameters) && !objectIsEmpty(requestBody)) {
      operationReturn = service.constructor.prototype[operationId].call(
        service,
        requestBody,
        ...parameters
      );
    } else {
      operationReturn =
        service.constructor.prototype[operationId].call(service);
    }
    return operationReturn;
  }

}
