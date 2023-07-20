import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Address, CEPQuery, CityResponse, StateResponse } from '../models/address';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}
  private zipCodeURL: string =
    'https://viacep.com.br/ws';
  private addressURL: string = 'http://localhost:9000/address';
  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  fetchAddressFromZipCode(zipCode: string) {
    return this.http.get<CEPQuery>(`${this.zipCodeURL}/${zipCode}/json`, this.options).pipe(
      catchError(this.errorHandling.handleError<CEPQuery>('fetchAddressFromZipCode', {} as CEPQuery))
    );
  }

  getAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(this.addressURL, this.options).pipe(
      catchError(this.errorHandling.handleError<Address[]>('getAddresses', []))
    )
  }

  getAddressById(id: number) {
    return this.http.get<Address>(`${this.addressURL}/${id}`, this.options).pipe(
      catchError(this.errorHandling.handleError<Address>('getAddressById', {} as Address))
    )
  }

  createAddress(address: any): Observable<Address> {
    return this.http.post<Address>(this.addressURL, address, this.options).pipe(
      catchError(this.errorHandling.handleError<Address>('createAddress', {} as Address))
    );
  }


  deleteAddress(addressId: number): Observable<any> {
    return this.http.delete<any>(`${this.addressURL}/${addressId}`, this.options).pipe(
      catchError(this.errorHandling.handleError<any>('deleteAddress', {}))
    );
  }

  updateAddress(address: Address): Observable<Address> {
    return this.http.put<Address>(`${this.addressURL}/${address.id}`, address, this.options).pipe(
      catchError(this.errorHandling.handleError<Address>('editAddress', address))
    );
  }
}
