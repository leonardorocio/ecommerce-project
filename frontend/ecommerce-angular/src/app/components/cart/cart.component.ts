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
export class CartComponent implements OnInit {
  constructor(
    private orderService: OrderService,
    private orderDetailsService: OrderDetailsService,
    private shipperService: ShipperService,
    private shipmentService: ShipmentService,
    private router: Router,
    private toastr: ToastrService
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
      if ('productId' in history.state) {
        this.addProductToCart();
      }
    });

    this.shipperService.getShippers().subscribe((shippers) => {
      this.shippers = shippers;
      this.selectedShip = shippers[0];
      this.shipments = this.generateShipments();
    });
  }

  addProductToCart() {
    let { navigationId: _, ...product } = history.state;
    let orderDetailsFiltered = this.items.filter(
      (item) => JSON.stringify(item.product) === JSON.stringify(product)
    )[0];
    if (!this.items.includes(orderDetailsFiltered)) {
      this.orderDetailsService
        .createOrderDetails(this.order, history.state, 1)
        .subscribe((orderDetails) => this.items.push(orderDetails));
    } else {
      this.updateQuantity(
        orderDetailsFiltered,
        1,
        this.items.indexOf(orderDetailsFiltered)
      );
    }
  }

  generateShipments(): any {
    let map = this.shippers.map((shipper) => [
      shipper.name,
      this.shipmentService.generateShipmentOption(this.order, shipper),
    ]);
    let a = Object.fromEntries(map);
    return a;
  }

  updateQuantity(orderDetails: OrderDetails, update: number, index: number) {
    orderDetails.order = this.order;
    this.orderDetailsService
      .updateOrderDetails(orderDetails, update)
      .subscribe((orderDetails) => {
        this.items[index] = orderDetails;
      });
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  finishOrder() {
    this.toastr
      .success('Voltando ao início...', 'Compra finalizada com sucesso!')
      .onShown.subscribe(() => this.goToDashboard());
  }

  clearCart(order: Order) {
    Swal.fire({
      title: 'Deseja apagar todos os produtos do carrinho?',
      text: 'Essa ação é irreversível!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
    } as SweetAlertOptions).then((result) => {
      if (result.value) {
        this.orderService.deleteOrder(order.orderId).subscribe(() => {
          history.replaceState({}, '');
          window.location.reload();
        });
      }
    });
  }

  removeProduct(orderDetails: OrderDetails) {
    if (this.items.length == 1) {
      this.clearCart(this.order);
    } else {
      Swal.fire({
        title: 'Deseja apagar esse produto do carrinho?',
        text: 'Essa ação é irreversível!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Confirmar',
      } as SweetAlertOptions).then((result) => {
        if (result.value) {
          this.orderDetailsService
            .deleteOrderDetails(orderDetails.orderDetailsId)
            .subscribe(() => {
              this.items = this.items.filter((o) => o !== orderDetails);
              window.location.reload();
            });
        }
      });
    }
  }
}
