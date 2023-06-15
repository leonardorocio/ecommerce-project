import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, catchError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Comment } from '../models/comment';
import { User } from '../models/user';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}

  baseCommentURL = 'http://localhost:9000/comments';
  options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  createComment(
    text: string,
    rating: number,
    userId: number,
    productId: number
  ): Observable<Comment> {
    let comment = {
      text: text,
      rating: rating,
      user_owner: userId,
      product_rated: productId,
    };
    return this.http
      .post<Comment>(`${this.baseCommentURL}`, comment, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError('createComment', {} as Comment)
        )
      );
  }

  editComment(comment: any): Observable<Comment> {
    return this.http
      .put<Comment>(
        `${this.baseCommentURL}/${comment.commentId}`,
        comment,
        this.options
      )
      .pipe(
        catchError(
          this.errorHandling.handleError<Comment>('editComment', comment)
        )
      );
  }

  deleteComment(commentId: number): Observable<any> {
    return this.http
      .delete<Comment>(`${this.baseCommentURL}/${commentId}`, this.options)
      .pipe(
        catchError(this.errorHandling.handleError<Comment>('deleteComment'))
      );
  }

  getCommentsFromProduct(productId: number): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.baseCommentURL}/product/${productId}`, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<Comment[]>('getCommentsFromProduct', [])
        )
      );
  }
}
