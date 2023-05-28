import { Component } from '@angular/core';
import { DataSet } from '../../Models/DataSet';
import { DataSetService } from '../../services/data-set.service';
import { LoginService } from 'src/app/services/login-service.service';
@Component({
  selector: 'app-profil-window',
  templateUrl: './profil-window.component.html',
  styleUrls: ['./profil-window.component.css'],
})
export class ProfilWindowComponent {
  datenArray: Array<DataSet> = [];
  currentUser: any;
  constructor(
    private dataSetService: DataSetService,
    private loginService: LoginService
  ) {
    this.getData();

    this.loginService.getCurrentUser().subscribe({
      next: (data) => {
        console.log('##### ', data);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  getData(): void {
    this.dataSetService.getDataSetList().subscribe((data) => {
      this.datenArray = data;
    });
  }
}
