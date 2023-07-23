import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, tap, of } from 'rxjs';
import { AuthRequestBody, AuthResponseBody } from '../models/auth';
import { ErrorHandlingService } from './error-handling.service';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private errorHandling: ErrorHandlingService,
    private cookieService: CookieService
  ) {}

  authURL: string = 'http://localhost:9000/auth';
  httpOptions = {
    headers: new HttpHeaders('Content-Type: application/json'),
  };



  login(requestBody: any): Observable<AuthResponseBody> {
    return this.http
      .post<AuthResponseBody>(
        `${this.authURL}/login`,
        requestBody,
        this.httpOptions
      )
      .pipe(
        catchError(
          this.errorHandling.handleError<AuthResponseBody>(
            'login',
            {} as AuthResponseBody
          )
        )
      );
  }

  getRefreshToken(requestBody: any): Observable<AuthResponseBody> {
    return this.http
      .post<AuthResponseBody>(
        `${this.authURL}/refreshtoken`,
        requestBody,
        this.httpOptions
      )
      .pipe(
        catchError(
          this.errorHandling.handleError<AuthResponseBody>(
            'getRefreshToken',
            {} as AuthResponseBody
          )
        ),
      );
  }

  getTokenOrRefreshed(): Observable<string> {
    return new Observable<string>((observer) => {
      const token: string = this.cookieService.check("accessToken") ? this.cookieService.get("accessToken") : '';
      const expiryDateTime: string = this.cookieService.check("expiryDate") ? this.cookieService.get("expiryDate") : '';
      const actualDateTime: string = new Date(Date.now()).toISOString();

      if (token && actualDateTime > expiryDateTime) {
        this.getRefreshToken({ refreshToken: this.cookieService.get("refreshToken")}).subscribe((data) => {
          this.cookieService.set("accessToken", data.accessToken);
          observer.next(data.accessToken);
          observer.complete();
        });
      } else {
        observer.next(token);
        observer.complete();
      }
    })
  }
}
