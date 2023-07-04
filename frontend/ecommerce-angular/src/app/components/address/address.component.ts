import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Address, CityResponse, StateResponse } from 'src/app/models/address';
import { User } from 'src/app/models/user';
import { AddressService } from 'src/app/services/address.service';
import { AlertService } from 'src/app/services/alert.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css'],
})
export class AddressComponent implements OnInit {
  constructor(
    public addressService: AddressService,
    private toastr: ToastrService,
    private alert: AlertService
  ) {}

  user: User = JSON.parse(localStorage['user']);
  addresses: Address[] = this.user.addressList;
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
  addingAddress: boolean = false;
  editingAddress: boolean = false;
  dropDownShow: boolean = false;

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

  enableAddressForm(
    option: boolean,
    operation: 'editing' | 'creating' | 'closing'
  ) {
    this.editingAddress = operation === 'editing';
    if (operation === 'creating') {
      ({
        zipCode: this.zipCode,
        streetWithNumber: this.streetWithNumber,
        state: this.selectedState,
        city: this.selectedCity,
      } = {
        zipCode: '',
        streetWithNumber: '',
        city: '',
        state: {} as StateResponse,
      });
    }
    this.addingAddress = option;
  }

  createAddress(
    zipCode: string,
    streetWithNumber: string,
    state: StateResponse,
    city: string
  ) {
    const userId: number = this.user.userId;
    const address = {
      zipCode: zipCode,
      streetWithNumber: streetWithNumber,
      state: state.nome,
      city: city,
      userId: userId,
    } as Address;
    this.addressService.createAddress(address).subscribe((address) => {
      this.addresses.push(address);
      this.selectedAddress = address;
      localStorage['user'] = JSON.stringify(this.user);
    });
    this.enableAddressForm(false, 'closing');
  }

  selectCity(city: CityResponse) {
    this.selectedCity = city.nome;
    this.searchCities(this.selectedCity);
    this.dropDownShow = false;
  }

  searchCities(term: string) {
    this.filteredCities = this.addressService.filterCities(this.cities, term);
  }

  verifyAndSendAddressForm(
    cep: NgModel,
    street: NgModel,
    state: NgModel,
    city: NgModel
  ) {
    const fields: NgModel[] = [cep, street, state, city].filter(
      (field) => field.errors != null
    );
    if (fields.length > 0) {
      fields.forEach((field) => {
        this.toastr.error(`${field.name} é obrigatório`, 'Erro');
      });
    } else {
      if (this.editingAddress) {
        this.editAddress(cep.value, street.value, state.value, city.value);
      } else {
        this.createAddress(cep.value, street.value, state.value, city.value);
      }
    }
  }

  editAddress(
    zipCode: string,
    streetWithNumber: string,
    state: StateResponse,
    city: string
  ) {
    const addressBody: Address = {
      zipCode: zipCode,
      streetWithNumber: streetWithNumber,
      state: state.nome,
      city: city,
      userId: this.user.userId,
      addressId: this.selectedAddress.addressId,
    };
    this.selectedAddress = { ...addressBody };
    this.addressService
      .editAddress(this.selectedAddress)
      .subscribe((editedAddress) => {
        const addressIndex = this.addresses.findIndex(
          (address) => address.addressId === editedAddress.addressId
        );
        this.addresses[addressIndex] = editedAddress;
        this.selectedAddress = editedAddress;
        localStorage['user'] = JSON.stringify(this.user);
        this.toastr.success('Endereço editado com sucesso!', 'OK');
      });
    this.enableAddressForm(false, 'closing');
  }

  enableEditingAddress(address: Address) {
    let tempState: string;
    ({
      zipCode: this.zipCode,
      streetWithNumber: this.streetWithNumber,
      state: tempState,
      city: this.selectedCity,
    } = address);
    this.selectedState =
      this.states.find((state) => state.nome === tempState) ??
      ({} as StateResponse);
    this.enableAddressForm(true, 'editing');
  }

  async deleteAddress(addressToDelete: Address) {
    const result = await this.alert.warning(
      'Cuidado!',
      'Deseja excluir esse endereço?'
    );
    if (result) {
      this.addressService
        .deleteAddress(addressToDelete.addressId)
        .subscribe(() => {
          const addressIndex = this.addresses.findIndex(
            (address) => address.addressId === addressToDelete.addressId
          );
          this.addresses.splice(addressIndex, 1);
          this.selectedAddress = this.addresses[0];
          localStorage['user'] = JSON.stringify(this.user);
        });
    }
  }
}
