import { Component } from '@angular/core';
import { DataSet } from "../../Models/DataSet";
import { DataSetService } from "../../services/data-set.service";
@Component({
  selector: 'app-profil-window',
  templateUrl: './profil-window.component.html',
  styleUrls: ['./profil-window.component.css']
})
export class ProfilWindowComponent {
  datenArray: Array<DataSet> = [];
  constructor(private dataSetService: DataSetService) {
    this.getData();
  }

  getData(): void {
    this.dataSetService.getDataSetList().subscribe((data) => {
      this.datenArray = data;
    });
  }
}
