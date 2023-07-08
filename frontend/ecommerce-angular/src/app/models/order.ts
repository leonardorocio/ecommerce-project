import { Product } from "./product";
import { User } from "./user";
import { Shipment } from "./shipment";

export interface OrderDetails {
  id: number
  order: Order,
  product: Product,
  quantity: number
}

export interface Order {
  id: number,
  orderedDate: string,
  totalPrice: number,
  orderDetailsList: OrderDetails[],
  closed: boolean,
  shipment: Shipment
}
