import { CanActivateFn, Router, mapToCanMatch } from '@angular/router';
import { User } from '../models/user';
import { inject } from '@angular/core';
import { AlertService } from '../services/alert.service';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from '../services/auth.service';

const redirectToLoginOrDashboard = async () => {
  const router = inject(Router);
  return inject(AlertService)
    .question('Usuário não autenticado', "Deseja ir para a página de login")
    .then((result) => {
      if (result) {
        return router.parseUrl('/auth');
      }
      return router.parseUrl('/dashboard');
    });
};

export const isLoggedGuard: CanActivateFn = (route, state) => {
  var user: User = {} as User;
  const cookieService = inject(CookieService);
  const authService = inject(AuthService);
  if (cookieService.check("user")) {
    user = JSON.parse(cookieService.get("user"));
    const token: string = cookieService.check("accessToken") ? cookieService.get("accessToken") : '';
    const expiryDateTime: string = cookieService.check("expiryDate") ? cookieService.get("expiryDate") : '';
    const actualDateTime: string = new Date(Date.now()).toISOString();

    if (!token) {
      return redirectToLoginOrDashboard();
    } else if (actualDateTime > expiryDateTime) {
      authService.getTokenOrRefreshed().subscribe();
      return redirectToLoginOrDashboard();
    }
  }
  return Object.keys(user).length > 0 ? true : redirectToLoginOrDashboard();
};
