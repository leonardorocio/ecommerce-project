import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Order, OrderDetails } from 'src/app/models/order';
import { User } from 'src/app/models/user';
import { OrderDetailsService } from 'src/app/services/order-details.service';
import { OrderService } from 'src/app/services/order.service';
import { Address } from 'src/app/models/address';
import { ShipperService } from 'src/app/services/shipper.service';
import { Shipper } from 'src/app/models/shipper';
import { ShipmentService } from 'src/app/services/shipment.service';
import { Shipment } from 'src/app/models/shipment';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  constructor(
    private orderService: OrderService,
    private orderDetailsService: OrderDetailsService,
    private shipperService: ShipperService,
    private shipmentService: ShipmentService
  ) {}

  order!: Order;
  items!: OrderDetails[];
  addresses: Address[] = JSON.parse(localStorage['user']).addressList;
  selectedAddress: Address = this.addresses[0]
    ? this.addresses[0]
    : ({} as Address);
  shippers!: Shipper[];
  selectedShip!: Shipper;
  shipments!: any;

  private user: User = JSON.parse(localStorage['user']);

  ngOnInit(): void {
    this.orderService.getOrderFromUser(this.user.userId).subscribe((orders) => {
      this.order = orders.filter((o) => !o.closed)[0];
      if (!this.order) {
        this.orderService.createOrder(this.user.userId).subscribe((order) => {
          this.order = order;
        });
      }
      this.items = this.order.orderDetailsId;
    });
    this.shipperService.getShippers().subscribe((shippers) => {
      this.shippers = shippers;
      this.selectedShip = shippers[0];
      this.shipments = this.generateShipments();
    });
  }

  generateShipments(): any {
    let map = this.shippers.map((shipper) => [
      shipper.name,
      this.shipmentService.generateShipmentOption(this.order, shipper),
    ]);
    let a = Object.fromEntries(map);
    console.log(a);
    return a;
  }

  updateQuantity(orderDetails: OrderDetails, index: number) {
    orderDetails.order = this.order;
    this.orderDetailsService
      .updateOrderDetails(orderDetails)
      .subscribe((orderDetails) => {
        this.items[index] = orderDetails;
      });
  }

  // generateShipment(shipper: Shipper) {
  //   this.shipment = this.shipmentService.generateShipmentOption(this.order, shipper);
  // }
}
