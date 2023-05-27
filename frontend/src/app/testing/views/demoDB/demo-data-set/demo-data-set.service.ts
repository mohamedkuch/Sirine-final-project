import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {DataSetInfo} from "../../../Models/dataSetInfo";

@Injectable({
  providedIn: "root"
})
export class DemoDataSetService {
  private baseURI: string = "http://localhost:8080/testData";

  constructor(private http: HttpClient) {}

  public uploadDataSet(file: File): Promise<any> {
    const formData = new FormData();
    formData.append("file", file, file.name);

    const headers = new HttpHeaders();
    headers.append("Content-Type", "multipart/form-data");
    headers.append("Accept", "application/json");

    return this.http.post(`${this.baseURI}/dataSets/uploadFile`, formData, {headers: headers}).toPromise();
  }

  public getDatasetList() {
    return this.http.get<DataSetInfo[]>(this.baseURI + "/dataSets/listDatasets");
  }
}
