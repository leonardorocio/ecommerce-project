import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';

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
    private userService: UserService,
    private router: Router
  ) {}

  login(email: string, password: string) {
    this.authService.login(email, password).subscribe((data) => {
      Object.keys(data).forEach((e) => {
        const value = data[e as keyof typeof data];
        localStorage.setItem(
          e,
          typeof value == 'string' ? value : JSON.stringify(value)
        );
      });
      this.toastr.success('Redirecionando...', 'Login Realizado com sucesso!');
      this.router.navigateByUrl('/dashboard');
    });
  }

  createUser(name: string, dateBirth: string, email: string, password: string) {
    const newUser: User = {
      name: name,
      email: email,
      birthDate: new Date(dateBirth).toISOString(),
      password: password,
    } as User;
    this.userService.createUser(newUser).subscribe((user) => {
      this.toastr
        .success('Indo para o Login...', 'Usuário criado com sucesso')
        .onShown.subscribe(() => this.changeView());
    });
  }

  changeView() {
    this.toggledRegister = !this.toggledRegister;
  }
}