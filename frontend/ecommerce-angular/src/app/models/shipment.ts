import { Order } from "./order";
import { Shipper } from "./shipper";

export interface Shipment {
  shipmentId: number,
  delivered: boolean,
  shippingPrice: number,
  expectedDeliveryDate: string,
  shipper: Shipper,
  order: Order
}
