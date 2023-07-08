import { Address } from "./address";
import { Comment } from "./comment";
import { Order } from "./order";

export interface User {
  id: number,
  name: string,
  email: string,
  password: string,
  birthDate: string,
  addressList: Address[],
  userOrders: Order[],
  role: string
}
