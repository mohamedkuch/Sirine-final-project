import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login-service.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
})
export class ToolbarComponent {
  constructor(private loginService: LoginService, private router: Router) {}
  ngOnInit(): void {}

  onLogout(): void {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}
