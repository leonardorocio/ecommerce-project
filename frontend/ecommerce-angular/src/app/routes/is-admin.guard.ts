import { CanActivate, CanActivateFn, Router, UrlTree } from '@angular/router';
import { User } from '../models/user';
import { inject, runInInjectionContext } from '@angular/core';
import { AlertService } from '../services/alert.service';


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
  if (sessionStorage['user'] !== undefined) {
    user = JSON.parse(sessionStorage['user']);
  }
  return Object.keys(user).length > 0 && user.role === 'ROLE_ADMIN' ? true : redirectToDashboard();
};

