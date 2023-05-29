import { Component } from '@angular/core';
import { DataSet } from '../../Models/DataSet';
import { DataSetService } from '../../services/data-set.service';
import { LoginService } from 'src/app/services/login-service.service';
import { User } from 'src/app/Models/User';
import { Observable } from 'rxjs';
import { UserRole } from 'src/app/Models/Role';
@Component({
  selector: 'app-profil-window',
  templateUrl: './profil-window.component.html',
  styleUrls: ['./profil-window.component.css'],
})
export class ProfilWindowComponent {
  datenArray: Array<DataSet> = [];
  currentUser$: Observable<User> = new Observable<User>();

  UserRole = UserRole;
  constructor(
    private dataSetService: DataSetService,
    private loginService: LoginService
  ) {
    this.getData();
    this.currentUser$ = this.loginService.getCurrentUser();
  }

  getData(): void {
    this.dataSetService.getDataSetList().subscribe((data) => {
      this.datenArray = data;
    });
  }
}
