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

  getComments(): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.baseCommentURL, this.options).pipe(
      catchError(this.errorHandling.handleError<Comment[]>('getComments', []))
    );
  }

  postComment(comment: any): Observable<Comment> {
    return this.http
      .post<Comment>(`${this.baseCommentURL}`, comment, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<Comment>('createComment', {} as Comment)
        )
      );
  }

  updateComment(comment: any, id: number): Observable<Comment> {
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

  getCommentsByProduct(id: number): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.baseCommentURL}/product/${id}`, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<Comment[]>(
            'getCommentsFromProduct',
            []
          )
        )
      );
  }

  getCommentsByUser(id: number): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.baseCommentURL}/users/${id}`, this.options)
      .pipe(
        catchError(
          this.errorHandling.handleError<Comment[]>('getCommentsFromUser', [])
        )
      );
  }
}
