import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, Subject, catchError, defaultIfEmpty, filter, of } from 'rxjs';
import { Order } from '../models/order';
import { Favorite } from '../models/favorite';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private errorHandlingService: ErrorHandlingService, private http: HttpClient) {}

  private userBaseURL = 'http://localhost:9000/users';
  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userBaseURL, this.options).pipe(
      catchError(this.errorHandlingService.handleError<User[]>('getUsers', []))
    );
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.userBaseURL}/${id}`, this.options).pipe(
      catchError(this.errorHandlingService.handleError<User>('getUserById', {} as User))
    )
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.userBaseURL, user, this.options).pipe(
      catchError(this.errorHandlingService.handleError<User>('createUser', {} as User))
    );
  }

  updateUser(body: any, id : number): Observable<User> {
    return this.http.patch<User>(`${this.userBaseURL}/${id}`, body, this.options).pipe(
      catchError(this.errorHandlingService.handleError<User>('updateUser', {} as User))
    );
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.userBaseURL}/${id}`, this.options).pipe(
      catchError(this.errorHandlingService.handleError<void>('updateUser'))
    );
  }

  changeUserPassword(body: any, id : number): Observable<string> {
    return this.http.patch<string>(`${this.userBaseURL}/change_password/${id}`, body, this.options).pipe(
      catchError(this.errorHandlingService.handleError<string>('updateUser', ''))
    );
  }

  getUsersOrders(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.userBaseURL}/${userId}/orders`, this.options).pipe(
      catchError(this.errorHandlingService.handleError<Order[]>('getOrderFromUser', []))
    )
  }

  getCommentsByUser(id: number): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(`${this.userBaseURL}/${id}/comments`, this.options)
      .pipe(
        catchError(
          this.errorHandlingService.handleError<Comment[]>('getCommentsFromUser', [])
        )
      );
  }

  getUsersFavorites(id: number): Observable<Favorite[]> {
    return this.http.get<Favorite[]>(`${this.userBaseURL}/${id}/favorite`, this.options).pipe(
      catchError(this.errorHandlingService.handleError<Favorite[]>('getUsersFavorites', []))
    );
  }
}
