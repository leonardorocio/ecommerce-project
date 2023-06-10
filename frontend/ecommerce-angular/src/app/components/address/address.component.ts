import { Component, OnInit } from '@angular/core';
import { Address, CityResponse, StateResponse } from 'src/app/models/address';
import { AddressService } from 'src/app/services/address.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css'],
})
export class AddressComponent implements OnInit {
  constructor(private addressService: AddressService) {}

  addresses: Address[] = JSON.parse(localStorage['user']).addressList;
  states!: StateResponse[];
  cities!: CityResponse[];
  selectedAddress: Address = this.addresses[0]
    ? this.addresses[0]
    : ({} as Address);

  zipCode!: string;
  streetWithNumber!: string;
  selectedState!: StateResponse;
  selectedCity!: CityResponse;

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
      });
  }

  createAddress(
    zipCode: string,
    streetWithNumber: string,
    state: StateResponse,
    city: CityResponse
  ) {
    const userId: number = Number.parseInt(
      JSON.parse(localStorage['user']).userId
    );
    const address = {
      zipCode: zipCode,
      streetWithNumber: streetWithNumber,
      state: state.nome,
      city: city.nome,
      userId: userId,
    } as Address;
    this.addressService.createAddress(address).subscribe((address) => {
      this.addresses.push(address);
      window.location.reload();
    });
  }
}
