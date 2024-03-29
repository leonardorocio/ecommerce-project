import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { switchMap, tap } from 'rxjs';
import { Cart } from 'src/app/models/cart';
import { Order } from 'src/app/models/order';
import { Shipment } from 'src/app/models/shipment';
import { AlertService } from 'src/app/services/alert.service';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';
import { ShipmentService } from 'src/app/services/shipment.service';
import Swal, { SweetAlertOptions } from 'sweetalert2';

@Component({
  selector: 'app-cart-summary',
  templateUrl: './cart-summary.component.html',
  styleUrls: ['./cart-summary.component.css'],
})
export class CartSummaryComponent {
  constructor(
    private router: Router,
    private orderService: OrderService,
    private toastr: ToastrService,
    private shipmentService: ShipmentService,
    private alert: AlertService
  ) {}

  @Input() order!: Order;
  @Input() shipment!: Shipment;

  orderTotalPrice(): number {
    return (this.order.totalPrice =
      this.order.orderDetailsList.reduce<number>(
        (acc, orderDetails) =>
          orderDetails.quantity *
            orderDetails.product.price *
            orderDetails.product.discount +
          acc,
        0
      ) + this.shipment.shippingPrice);
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  async finishOrder() {
    const result = await this.alert.question('Deseja finalizar a compra?');
    if (result) {
      const shipmentBody = {
        orderId: this.order.id,
        shipperId: this.shipment.shipper.id,
        shippingPrice: this.shipment.shippingPrice,
        expectedDeliveryDate: this.shipment.expectedDeliveryDate,
      };
      this.shipmentService
        .postShipment(shipmentBody)
        .pipe(
          tap((shipment) => (this.order.shipment = shipment)),
          switchMap((shipment) =>
            this.orderService.closeOrder(this.order.id)
          )
        )
        .subscribe((order) => {
          this.order = order;
          this.toastr
            .success('Voltando ao início...', 'Compra finalizada com sucesso')
            .onShown.subscribe(() => {
              this.goToDashboard();
            });
        });
    }
  }
}
