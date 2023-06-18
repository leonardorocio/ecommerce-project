import { Product } from "./product";
import { User } from "./user";
import { Shipment } from "./shipment";

export interface OrderDetails {
  orderDetailsId: number
  order: Order,
  product: Product,
  quantity: number
}

export interface Order {
  orderId: number,
  orderedDate: string,
  totalPrice: number,
  // customer: User,
  orderDetailsId: OrderDetails[],
  closed: boolean,
  shipment: Shipment
}
