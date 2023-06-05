import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string = localStorage['accessToken'];
    const expiryDateTime: string = localStorage['expiryDate'];
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
