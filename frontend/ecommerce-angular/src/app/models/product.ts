import { Comment } from "./comment";
import { Category } from "./category";

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  discount: number;
  stock: number;
  comments: Comment[],
  productCategory: Category,
  productImage: string
}
