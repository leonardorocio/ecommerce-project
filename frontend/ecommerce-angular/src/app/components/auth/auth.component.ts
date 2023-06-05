import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { trigger, style, animate, transition } from '@angular/animations';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent {
  toggledRegister: boolean = false;

  constructor(
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  login(email: string, password: string) {
    this.authService.login(email, password).subscribe((data) => {
      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('refreshToken', data.refreshToken);
      localStorage.setItem('expiryDate', data.expiryDate);
      this.authService.accessToken = data.accessToken;
      this.toastr.success('Redirecionando...', 'Login Realizado com sucesso!');
      this.router.navigate(['/dashboard']);
    });
  }

  changeView() {
    this.toggledRegister = !this.toggledRegister;
  }
}
