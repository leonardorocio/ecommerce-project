import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, tap, of } from 'rxjs';
import { AuthRequestBody, AuthResponseBody } from '../models/auth';
import { ErrorHandlingService } from './error-handling.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private errorHandling: ErrorHandlingService) { }

  accessToken: string = '';
  authURL: string = 'http://localhost:9000/auth';
  httpOptions = {
    headers: new HttpHeaders('Content-Type: application/json')
  };

  login(email: string, password: string): Observable<AuthResponseBody> {
    let requestBody: AuthRequestBody = {
      email: email,
      password: password
    };
    return this.http.post<AuthResponseBody>(`${this.authURL}/login`, requestBody, this.httpOptions).pipe(
      catchError(this.errorHandling.handleError<AuthResponseBody>('login'))
    );
  }

  async getAccessToken(): Promise<string> {
    const item = localStorage.getItem('accessToken');
    return item ? item : '';
  }

}
