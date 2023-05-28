import { Component } from '@angular/core';
import { LoginService } from '../../services/login-service.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login-window',
  templateUrl: './login-window.component.html',
  styleUrls: ['./login-window.component.scss'],
})
export class LoginWindowComponent {
  isUser: boolean = true;

  email: string = '';
  password: string = '';

  errorText: string = '';

  constructor(private loginService: LoginService, private router: Router) {
    if (this.loginService.loggedIn()) {
      this.router.navigate(['/datasets']);
    }
  }

  login() {
    this.loginService.loginUser(this.email, this.password).subscribe({
      next: (res: any) => {
        // save token in local storage
        localStorage.setItem('token', res['token']);
        this.router.navigate(['/datasets']);
        this.errorText = '';
      },
      error: () => {
        this.errorText = 'Error : Invalid email or password';
      },
    });
  }
}
