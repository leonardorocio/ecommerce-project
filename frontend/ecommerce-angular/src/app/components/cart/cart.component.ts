import { Component, OnInit } from '@angular/core';
import { Order, OrderDetails } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { OrderDetailsService } from 'src/app/services/order-details.service';
import { OrderService } from 'src/app/services/order.service';
import { Address } from 'src/app/models/address';
import { ShipperService } from 'src/app/services/shipper.service';
import { Shipper } from 'src/app/models/shipper';
import { ShipmentService } from 'src/app/services/shipment.service';
import { Shipment } from 'src/app/models/shipment';
import { Product } from 'src/app/models/product';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal, { SweetAlertOptions } from 'sweetalert2';
import { AddressComponent } from '../address/address.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent {
  constructor(
    private router: Router,
    private toastr: ToastrService
  ) {}

  order!: Order;
  empty = false;
  user: User = JSON.parse(localStorage['user']);
  shipment!: Shipment;

  cartOrder(order: Order) {
    this.order = order;
    this.empty = this.order.orderDetailsId.length == 0;
  }

  selectShipment(shipment: Shipment) {
    this.shipment = shipment;
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  finishOrder() {
    this.toastr
      .success('Voltando ao início...', 'Compra finalizada com sucesso!')
      .onShown.subscribe(() => this.goToDashboard());
  }

}
