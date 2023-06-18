import { User } from "./user";

export interface Comment {
  commentId: number,
  rating: number,
  text: string,
  userOwner: User
}
