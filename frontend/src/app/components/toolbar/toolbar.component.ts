import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserRole } from 'src/app/Models/Role';
import { User } from 'src/app/Models/User';
import { LoginService } from 'src/app/services/login-service.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
})
export class ToolbarComponent {
  currentUser$: Observable<User> = new Observable<User>();
  UserRole = UserRole;

  constructor(private loginService: LoginService, private router: Router) {
    this.currentUser$ = this.loginService.getCurrentUser();

  }
  ngOnInit(): void {}

  onLogout(): void {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}
