import { Component } from '@angular/core';
import { Address } from 'src/app/models/address';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent {
  addresses: Address[] = JSON.parse(localStorage['user']).addressList;
  selectedAddress: Address = this.addresses[0]
    ? this.addresses[0]
    : ({} as Address);

  cep!: string
}
