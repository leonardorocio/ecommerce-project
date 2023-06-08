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
      Object.keys(data).forEach((e) => {
        const value = data[e as keyof typeof data]
        localStorage.setItem(e, typeof value == 'string' ? value : JSON.stringify(value))
      });
      this.toastr.success('Redirecionando...', 'Login Realizado com sucesso!');
      this.router.navigate(['/dashboard']);
    });
  }

  changeView() {
    this.toggledRegister = !this.toggledRegister;
  }
}
