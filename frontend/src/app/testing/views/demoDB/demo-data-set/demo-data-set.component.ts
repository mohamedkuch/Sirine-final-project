import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { DemoDataSetService } from "./demo-data-set.service";

import { DataSetInfo} from "../../../Models/dataSetInfo";

@Component({
  selector: 'app-demo-data-set',
  templateUrl: './demo-data-set.component.html',
  styleUrls: ['./demo-data-set.component.css']
})
export class DemoDataSetComponent {

  constructor(private demoDBService: DemoDataSetService) { }


  ngOnInit():void {
    this.updateDataSetList();
  }

  dataSetList: DataSetInfo[]|any;

  @ViewChild('fileInput') fileInput: ElementRef | undefined;

  onFileSelect(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    const file = (inputElement.files as FileList)[0];
    console.log(this.dataSetList);
    this.demoDBService.uploadDataSet(file);
  }

  onSubmit() {
    if (this.fileInput && this.fileInput.nativeElement.files.length > 0) {
      const file = this.fileInput.nativeElement.files[0];
      this.demoDBService.uploadDataSet(file);
    }
  }

  public updateDataSetList() {
    this.demoDBService.getDatasetList()
      .subscribe(list => {
        this.dataSetList = list;
        console.log(this.dataSetList)
      });
  }
}
