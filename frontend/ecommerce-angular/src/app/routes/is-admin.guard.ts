import { CanActivate, CanActivateFn, Router, UrlTree } from '@angular/router';
import { User } from '../models/user';
import { inject, runInInjectionContext } from '@angular/core';
import { AlertService } from '../services/alert.service';
import { CookieService } from 'ngx-cookie-service';


const redirectToDashboard = async () => {
  const router = inject(Router);
  return inject(AlertService)
    .info('Área restrita', 'Voltando à página inicial')
    .then(() => {
      return router.parseUrl('/dashboard');
    });
};

export const isAdminGuard: CanActivateFn = (route, state) => {
  var user: User = {} as User;
  const cookieService = inject(CookieService);
  if (cookieService.check("user")) {
    user = JSON.parse(cookieService.get("user"));
  }
  return Object.keys(user).length > 0 && user.role === 'ROLE_ADMIN' ? true : redirectToDashboard();
};

