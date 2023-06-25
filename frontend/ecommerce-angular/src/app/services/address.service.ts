import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Address, CityResponse, StateResponse } from '../models/address';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}
  private locationsURL: string =
    'https://servicodados.ibge.gov.br/api/v1/localidades/estados';
  private addressURL: string = 'http://localhost:9000/address';
  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  getStates(): Observable<StateResponse[]> {
    return this.http
      .get<StateResponse[]>(this.locationsURL, this.options)
      .pipe(catchError(this.errorHandling.handleError<StateResponse[]>('getStates', [])));
  }

  getCitiesFromState(state: string): Observable<CityResponse[]> {
    return this.http
      .get<CityResponse[]>(`${this.locationsURL}/${state}/municipios`)
      .pipe(
        catchError(this.errorHandling.handleError<CityResponse[]>('getCitiesFromStates', []))
      );
  }

  createAddress(address: Address): Observable<Address> {
    return this.http.post<Address>(this.addressURL, address, this.options).pipe(
      catchError(this.errorHandling.handleError<Address>('createAddress', {} as Address))
    );
  }

  filterCities(cities: CityResponse[], filter: string): CityResponse[] {
    return cities.filter(
      (city) => city.nome.toLowerCase().indexOf(filter.toLowerCase()) > -1
    );
  }

  deleteAddress(addressId: number): Observable<any> {
    return this.http.delete<any>(`${this.addressURL}/${addressId}`, this.options).pipe(
      catchError(this.errorHandling.handleError<any>('deleteAddress', {}))
    );
  }

  editAddress(address: Address): Observable<Address> {
    return this.http.put<Address>(`${this.addressURL}/${address.addressId}`, address, this.options).pipe(
      catchError(this.errorHandling.handleError<Address>('editAddress', address))
    );
  }
}
