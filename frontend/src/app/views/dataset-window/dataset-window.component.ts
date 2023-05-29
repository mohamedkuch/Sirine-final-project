import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataSetService } from '../../services/data-set.service';
import { DataSet } from '../../Models/DataSet';
@Component({
  selector: 'app-dataset-window',
  templateUrl: './dataset-window.component.html',
  styleUrls: ['./dataset-window.component.scss'],
})
export class DatasetWindowComponent {
  isButtonClicked: Boolean = false;
  datenArray: Array<DataSet> = [];
  filteredData: Array<DataSet> = [];
  searchTerm: string = '';

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

  onSetFavoriteClick(index: number) {
    this.dataSetService.setFavoriteDataSet(
      this.datenArray[index].id.toString()
    ).subscribe({
      next: () => {
        this.datenArray[index].isFavorite = true;
      },
      error: (err) => console.log('Error setting favorite')
    })
  }

  onRemoveFavoriteClick(index: number) {
    this.dataSetService.RemoveFavoriteDataSet(
      this.datenArray[index].id.toString()
    ).subscribe({
      next: () => {
        this.datenArray[index].isFavorite = false;
      },
      error: (err) => console.log('Error deleting favorite')
    })
  }

  gotoDetail(id: number) {
    this.router.navigate(['/datasets', id]);
  }

  search(term: string): void {
    this.filteredData = this.datenArray.filter((item) =>
      item.name.toLowerCase().includes(term.toLowerCase())
    );
  }
}
