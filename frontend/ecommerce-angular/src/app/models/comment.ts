import { User } from "./user";

export interface Comment {
  id: number,
  rating: number,
  text: string,
  userOwner: User
}
