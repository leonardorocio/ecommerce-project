import { Comment } from "./comment";
import { Category } from "./category";
import { Brand } from "./brand";

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  discount: number;
  stock: number;
  comments: Comment[],
  productCategory: Category,
  brand: Brand,
  productImage: string
}
