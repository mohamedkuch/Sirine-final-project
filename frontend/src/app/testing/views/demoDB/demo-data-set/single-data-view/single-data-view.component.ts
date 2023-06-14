import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DemoDataSetService} from "../demo-data-set.service";
import {JsonEditorComponent,JsonEditorOptions} from "ang-jsoneditor";

@Component({
  selector: 'app-single-data-view',
  templateUrl: './single-data-view.component.html',
  styleUrls: ['./single-data-view.component.scss']
})
export class SingleDataViewComponent {
  id: bigint;
  data: String | undefined;
  jsonData: any;
  changeWindowOpen:boolean = false;
  isFavoriteDataset: boolean = false;

  editorOptions:JsonEditorOptions;
  @ViewChild(JsonEditorComponent, { static: true }) editor!: JsonEditorComponent;


  constructor(private route: ActivatedRoute, private dataSetService: DemoDataSetService) {
    this.id = BigInt(this.route.snapshot.paramMap.get("id")!!);
    this.getData();
    this.editorOptions = new JsonEditorOptions();
    this.editorOptions.modes = ['code', 'text', 'tree', 'view'];
  }
  ngAfterViewInit() {
    const changeDataDialog = document.getElementById("changeDataWindow") as HTMLDialogElement;
    const openDialogButton = document.getElementById("openChangeData");
    const closeDialogButton = document.getElementById("closeChangeData");
    const sendChangeButton = document.getElementById("sendChange");

    openDialogButton?.addEventListener('click', () => { if(!this.changeWindowOpen) {changeDataDialog.showModal(); this.changeWindowOpen = true} });
    closeDialogButton?.addEventListener('click', () => { if(this.changeWindowOpen) {changeDataDialog.close(); this.changeWindowOpen = false} });
    sendChangeButton?.addEventListener('click', () => {
      this.updateDataset();

      changeDataDialog.close();
      this.changeWindowOpen = false;
    } )
  }

  getData() {
    this.dataSetService.getDataset(this.id).subscribe((data) => {
        this.jsonData = data;
        console.log(this.jsonData);
    }, (error) => {
      console.error('Error fetching JSON data', error);
      }
    );
  }

  updateDataset() {
    this.jsonData = this.editor.get();

    console.log("send change");
    console.log(this.jsonData);
    let response=this.dataSetService.updateDataset(this.id.toString(), this.jsonData);
    if (response!=null){
      response.subscribe(
        response => {
          // Hier können Sie die Antwort verarbeiten
          alert(response);
          console.log(response);
        },
        error => {
          //TODO: ALLE Responses werden als "error" erkannt...

          // Hier können Sie Fehlerbehandlung durchführen
          console.error(error);
        }
      );

    }

  }


  onFavoriteClick(i: bigint) {
    this.isFavoriteDataset = !this.isFavoriteDataset;
  }
}
