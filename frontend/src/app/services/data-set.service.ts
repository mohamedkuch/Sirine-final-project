import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { DataSet } from "../Models/DataSet";

@Injectable({
  providedIn: "root",
})
export class DataSetService {
  public backendURL = 'http://localhost:8080/api/v1/datasets';

  constructor(private http: HttpClient) {}

  getDataSetList(): Observable<DataSet[]> {
    // work with local data and update logic in the future to get the data via the endpoint
    return this.http.get(this.backendURL).pipe(
      map((response: any) => {
        return response.map((item: any) => {
          const dataSet: DataSet = {
            id: item.id,
            name: item.name,
            isFavorite: item.isFavorite,
            isXML: item.isXML,
            isCSV: item.isCSV,
          };
          return dataSet;
        });
      })
    );
  }

  getSingleDataSet(id: string): Observable<any> {
    let url = "../../assets/data-set-single-example-" + id + ".json";
    return this.http.get(url);
  }
}
