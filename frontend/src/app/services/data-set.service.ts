import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { DataSet } from "../Models/DataSet";

@Injectable({
  providedIn: "root",
})
export class DataSetService {
  constructor(private http: HttpClient) {}

  getDataSetList(): Observable<DataSet[]> {
    // work with local data and update logic in the future to get the data via the endpoint
    return this.http.get("../../assets/data-set-list-example.json").pipe(
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
