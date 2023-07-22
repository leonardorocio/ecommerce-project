import {
  Component,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { Form, NgForm, NgModel } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { Address } from 'src/app/models/address';
import { User } from 'src/app/models/user';
import { AddressService } from 'src/app/services/address.service';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css'],
})
export class AddressComponent implements OnInit {
  constructor(
    public addressService: AddressService,
    private toastr: ToastrService,
    private alert: AlertService,
    private cookieService: CookieService,
  ) {}

  @ViewChild('addressForm', { static: false }) form!: NgForm;
  @Input() user!: User;
  addresses!: Address[];
  selectedAddress!: Address;

  cep!: string;
  addingAddress: boolean = false;
  editingAddress: boolean = false;

  ngOnInit(): void {
    this.addresses = this.user.addressList;
    this.selectedAddress = this.addresses[0] ?? ({} as Address);
  }

  fetchAddressFromZipCode(zipCode: NgModel) {
    const code: string = (zipCode.value as string).replace('-', '');
    this.addressService
      .fetchAddressFromZipCode(code)
      .subscribe((queryResult) => {
        this.form.setValue({
          street: queryResult.logradouro,
          city: queryResult.localidade,
          zipCode: queryResult.cep,
          state: queryResult.uf,
          complement: queryResult.complemento,
          number: null,
        });
      });
  }

  enableAddressForm(
    option: boolean,
    operation: 'editing' | 'creating' | 'closing'
  ) {
    this.editingAddress = operation === 'editing';
    this.addingAddress = option;
    if (operation === 'creating') {
      this.form.resetForm();
    }
  }

  createAddress() {
    const addressBody = { ...this.form.value, ...{ userId: this.user.id } };
    this.addressService.createAddress(addressBody).subscribe((address) => {
      console.log(address);
      this.addresses.push(address);
      this.selectedAddress = address;
      console.log(this.selectedAddress);
      this.cookieService.set('user', JSON.stringify(this.user));
      this.toastr.success('Endereço criado com sucesso!', 'OK');
    });
    this.enableAddressForm(false, 'closing');
  }

  verifyAndSendAddressForm() {
    if (this.form.errors === null) {
      if (this.editingAddress) {
        this.editAddress();
      } else {
        this.createAddress();
      }
    }
  }

  editAddress() {
    this.selectedAddress = {
      ...this.form.value,
      ...{ userId: this.user.id, id: this.selectedAddress.id },
    };
    this.addressService
      .updateAddress(this.selectedAddress)
      .subscribe((editedAddress) => {
        const addressIndex = this.addresses.findIndex(
          (address) => address.id === editedAddress.id
        );
        this.addresses[addressIndex] = editedAddress;
        this.selectedAddress = editedAddress;
        this.cookieService.set('user', JSON.stringify(this.user));
        this.toastr.success('Endereço editado com sucesso!', 'OK');
      });
    this.enableAddressForm(false, 'closing');
  }

  enableEditingAddress(address: Address) {
    this.enableAddressForm(true, 'editing');
    this.form.setValue({
      ...address,
    });
  }

  async deleteAddress(addressToDelete: Address) {
    const result = await this.alert.warning(
      'Cuidado!',
      'Deseja excluir esse endereço?'
    );
    if (result) {
      this.addressService.deleteAddress(addressToDelete.id).subscribe(() => {
        const addressIndex = this.addresses.findIndex(
          (address) => address.id === addressToDelete.id
        );
        this.addresses.splice(addressIndex, 1);
        this.selectedAddress = this.addresses[0];
        this.cookieService.set('user', JSON.stringify(this.user));
      });
    }
  }
}
