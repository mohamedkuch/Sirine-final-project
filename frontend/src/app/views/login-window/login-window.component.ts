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
  showTwoFactor: boolean = false;
  twoFactor: string = '';
  isLoading: boolean = false;

  constructor(private loginService: LoginService, private router: Router) {
    if (this.loginService.loggedIn()) {
      this.router.navigate(['/datasets']);
    }
  }

  submit() {
    if (this.showTwoFactor) {
      this.login();
    } else {
      this.twoFactorAuth();
    }
  }

  login() {
    this.loginService
      .loginUser(this.email, this.password, this.twoFactor)
      .subscribe({
        next: (res: any) => {
          console.log('#### login next', res);

          // save token in local storage
          localStorage.setItem('token', res['token']);
          this.router.navigate(['/datasets']);
          this.errorText = '';
        },
        error: (err: any) => {
          this.errorText = 'Error : Invalid email or password';

          if (err && err.error && err.error.message) {
            this.errorText = 'Error: ' + err.error.message;
          }
        },
      });
  }

  twoFactorAuth() {
    this.isLoading = true;
    this.loginService.twoFactorAuth(this.email, this.password).subscribe({
      next: (res: any) => {
        this.isLoading = false;
        this.errorText = '';
        this.showTwoFactor = true;
      },
      error: () => {
        this.isLoading = false;
        this.errorText = 'Error : Invalid email or password';
      },
    });
  }
}
