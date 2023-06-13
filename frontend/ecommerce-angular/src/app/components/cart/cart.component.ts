import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Order, OrderDetails } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { Shipment } from 'src/app/models/shipment';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

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
