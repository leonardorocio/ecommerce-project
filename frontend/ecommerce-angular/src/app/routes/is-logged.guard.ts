import { CanActivateFn, Router } from '@angular/router';
import { User } from '../models/user';
import { inject } from '@angular/core';
import { AlertService } from '../services/alert.service';

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
  if (sessionStorage['user'] !== undefined) {
    user = JSON.parse(sessionStorage['user']);
  }
  return Object.keys(user).length > 0 ? true : redirectToLoginOrDashboard();
};
