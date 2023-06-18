import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Order } from 'src/app/models/order';
import { Shipment } from 'src/app/models/shipment';
import { Shipper } from 'src/app/models/shipper';
import { ShipmentService } from 'src/app/services/shipment.service';
import { ShipperService } from 'src/app/services/shipper.service';

@Component({
  selector: 'app-shipping',
  templateUrl: './shipping.component.html',
  styleUrls: ['./shipping.component.css'],
})
export class ShippingComponent implements OnInit {
  constructor(
    private shipperService: ShipperService,
    private shipmentService: ShipmentService
  ) {}

  @Input() order!: Order;
  shippers!: Shipper[];
  selectedShip!: Shipper;
  shipments: any;
  @Output() selectedShipment = new EventEmitter<Shipment>();

  ngOnInit(): void {
    this.shipperService.getShippers().subscribe((shippers) => {
      this.shippers = shippers;
      this.selectedShip = shippers[0];
      this.shipments = this.generateShipments();
      this.selectShipment(this.selectedShip);
    });
  }

  selectShipment(shipper: Shipper) {
    this.selectedShip = shipper;
    this.selectedShipment.emit(this.shipments[shipper.name]);
  }

  generateShipments(): any {
    let map = this.shippers.map((shipper) => [
      shipper.name,
      this.shipmentService.generateShipmentOption(this.order, shipper),
    ]);
    let a = Object.fromEntries(map);
    return a;
  }
}
