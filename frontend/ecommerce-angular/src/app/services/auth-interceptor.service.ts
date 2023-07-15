import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private cookieService: CookieService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string = this.cookieService.check("accessToken") ? this.cookieService.get("accessToken") : '';
    const expiryDateTime: string = this.cookieService.check("expiryDate") ? this.cookieService.get("expiryDate") : '';
    const actualDateTime: string = new Date(Date.now()).toISOString();

    if (!token || actualDateTime > expiryDateTime) {
      return next.handle(req);
    }

    const req1 = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`),
    })

    return next.handle(req1);
  }
}
