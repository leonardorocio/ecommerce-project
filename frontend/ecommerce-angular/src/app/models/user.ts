import { Address } from "./address";
import { Comment } from "./comment";
import { Order } from "./order";

export interface User {
  id: number,
  name: string,
  email: string,
  password: string,
  accountCreationDate: string,
  birthDate: string,
  addressList: Address[],
  role: string
}
