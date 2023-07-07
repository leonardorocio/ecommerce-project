import { Order } from "./order";
import { Shipper } from "./shipper";

export interface Shipment {
  id: number,
  delivered: boolean,
  shippingPrice: number,
  expectedDeliveryDate: string,
  shipper: Shipper,
  order: Order
}
