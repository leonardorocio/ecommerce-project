import { Injectable } from '@angular/core';
import { Observable, switchMap, tap } from 'rxjs';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private authService: AuthService, private cookieService: CookieService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.includes("auth")) {
      return next.handle(req);
    }
    return this.authService.getTokenOrRefreshed().pipe(
      switchMap(token => {
        if (!token) {
          return next.handle(req);
        }

        const req1 = req.clone({
          headers: req.headers.set('Authorization', `Bearer ${token}`),
        })

        return next.handle(req1);
      })
    )



  }
}
