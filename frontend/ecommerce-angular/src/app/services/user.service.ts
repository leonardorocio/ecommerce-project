import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, Subject, catchError, defaultIfEmpty, filter, of } from 'rxjs';

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

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.userBaseURL, user, this.options).pipe(
      catchError(this.errorHandlingService.handleError<User>('createUser', {} as User))
    );
  }

  updateUser(id: number, name: string, date: string): Observable<User> {
    const body = {name: name, date: date};
    return this.http.patch<User>(`${this.userBaseURL}/${id}`, body, this.options).pipe(
      catchError(this.errorHandlingService.handleError<User>('updateUser', {} as User))
    );
  }
}
