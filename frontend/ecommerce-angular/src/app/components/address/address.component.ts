import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import {
  BehaviorSubject,
  debounceTime,
  defaultIfEmpty,
  distinctUntilChanged,
  distinctUntilKeyChanged,
} from 'rxjs';
import { Address, CityResponse, StateResponse } from 'src/app/models/address';
import { AddressService } from 'src/app/services/address.service';
import { PaginatorService } from 'src/app/services/paginator.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css'],
})
export class AddressComponent implements OnInit, OnChanges {
  constructor(
    public addressService: AddressService,
    public paginatorService: PaginatorService
  ) {}


  ngOnChanges(changes: SimpleChanges): void {
    if (changes['addresses']) {
      this.addresses = changes['addresses'].currentValue;
    }
  }

  addresses: Address[] = JSON.parse(localStorage['user']).addressList;
  states!: StateResponse[];
  cities!: CityResponse[];
  filteredCities!: CityResponse[];
  selectedAddress: Address = this.addresses[0]
    ? this.addresses[0]
    : ({} as Address);

  zipCode!: string;
  streetWithNumber!: string;
  selectedState!: StateResponse;
  selectedCity: string = '';

  ngOnInit(): void {
    this.addressService
      .getStates()
      .subscribe((states) => (this.states = states));
  }

  changeCities() {
    this.addressService
      .getCitiesFromState(this.selectedState.sigla)
      .subscribe((cities) => {
        this.cities = cities;
        this.filteredCities = cities;
      });
  }

  createAddress(
    zipCode: string,
    streetWithNumber: string,
    state: StateResponse,
    city: string
  ) {
    const userId: number = Number.parseInt(
      JSON.parse(localStorage['user']).userId
    );
    const address = {
      zipCode: zipCode,
      streetWithNumber: streetWithNumber,
      state: state.nome,
      city: city,
      userId: userId,
    } as Address;
    this.addressService.createAddress(address).subscribe((address) => {
      this.addresses.push(address);
    });
  }

  selectCity(city: CityResponse) {
    this.selectedCity = city.nome;
    this.searchCities(this.selectedCity);
    this.paginatorService.showDropdownMenu(false);
  }

  searchCities(term: string) {
    this.filteredCities = this.addressService.filterCities(this.cities, term);
  }
}
