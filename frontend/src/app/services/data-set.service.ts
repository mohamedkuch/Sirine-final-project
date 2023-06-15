import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { DataSet } from '../Models/DataSet';

@Injectable({
  providedIn: 'root',
})
export class DataSetService {
  constructor(private http: HttpClient) {}

  getDataSetList(): Observable<DataSet[]> {
    // work with local data and update logic in the future to get the data via the endpoint
    return this.http.get('http://localhost:8080/api/v1/datasets').pipe(
      map((response: any) => {
        return response.map((item: any, index: any) => {
          console.log('####', index);
          const dataSet: DataSet = {
            id: item.id,
            name: item.name,
            isFavorite: !!localStorage.getItem(`dataset${index}`),
            fileName: item.fileName,
            isXML: item.fileName.endsWith('.xml'),
            isCSV: item.fileName.endsWith('.csv'),
          };
          return dataSet;
        });
      })
    );
  }

  getSingleDataSet(id: string): Observable<any> {
    let url = 'http://localhost:8080/api/v1/datasets/' + id;
    return this.http.get(url);
  }
}
