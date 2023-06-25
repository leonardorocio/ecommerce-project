import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { switchMap, tap } from 'rxjs';
import { Cart } from 'src/app/models/cart';
import { Shipment } from 'src/app/models/shipment';
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
    private cartService: CartService,
    private toastr: ToastrService,
    private shipmentService: ShipmentService
  ) {}

  @Input() cart!: Cart;
  @Input() shipment!: Shipment;

  orderTotalPrice(): number {
    return (this.cart.order.totalPrice =
      this.cart.items.reduce<number>(
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
    const result = await Swal.fire({
      title: 'Deseja finalizar a compra?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#51A351',
      confirmButtonText: 'Confirmar',
    } as SweetAlertOptions);
    if (result.value) {
      const shipmentBody = {
        orderId: this.cart.order.orderId,
        shipperId: this.shipment.shipper.shipperId,
        shippingPrice: this.shipment.shippingPrice,
        expectedDeliveryDate: this.shipment.expectedDeliveryDate,
      };
      this.shipmentService
        .postShipment(shipmentBody)
        .pipe(
          tap((shipment) => (this.cart.order.shipment = shipment)),
          switchMap((shipment) =>
            this.orderService.finishOrder(this.cart.order.orderId)
          )
        )
        .subscribe((order) => {
          this.cart.order = order;
          this.cartService.updateLocalCart(this.cart);
          this.toastr
            .success('Voltando ao início...', 'Compra finalizada com sucesso')
            .onShown.subscribe(() => {
              this.goToDashboard();
            });
        });
    }
  }
}
