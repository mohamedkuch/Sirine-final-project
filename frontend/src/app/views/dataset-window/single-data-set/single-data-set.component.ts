import { Component } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import {DataSetService} from "../../../services/data-set.service";
@Component({
  selector: 'app-single-data-set',
  templateUrl: './single-data-set.component.html',
  styleUrls: ['./single-data-set.component.scss']
})
export class SingleDataSetComponent {
  id: string;
  item: any;
  itemKeys: string[] = [];
  itemValues: any[] = [];

  constructor(private route: ActivatedRoute, private dataSetService: DataSetService) {
    this.id = this.route.snapshot.paramMap.get("id")!!;
    this.getData();
  }

  getData() {
    this.dataSetService.getSingleDataSet(this.id).subscribe((data) => {
      this.item = data;
      this.itemKeys = Object.keys(data[0]);
      this.itemValues = data.map((obj: any) => Object.values(obj));
    });
  }
}
