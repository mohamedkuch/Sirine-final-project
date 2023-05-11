import { Component } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { DataSetService } from "../../services/data-set.service";
import { DataSet } from "../../Models/DataSet";
@Component({
  selector: 'app-dataset-window',
  templateUrl: './dataset-window.component.html',
  styleUrls: ['./dataset-window.component.scss']
})
export class DatasetWindowComponent {
  isButtonClicked: Boolean = false;
  datenArray: Array<DataSet> = [];
  filteredData: Array<DataSet> = [];
  searchTerm: string = "";

  constructor(private router: Router, private dataSetService: DataSetService) {
    this.getData();
  }

  getData(): void {
    this.dataSetService.getDataSetList().subscribe((data) => {
      this.datenArray = data;
    });
  }

  buttonIsClicked() {
    this.isButtonClicked = !this.isButtonClicked;
  }

  onFavoriteClick(index: number) {
    this.datenArray[index].isFavorite = !this.datenArray[index].isFavorite;
  }

  gotoDetail(id: number) {
    this.router.navigate(["/datasets", id]);
  }

  search(term: string): void {
    this.filteredData = this.datenArray.filter((item) =>
      item.name.toLowerCase().includes(term.toLowerCase())
    );
    console.log("#### search", this.searchTerm);
    console.log("#### search", this.filteredData);
  }
}
