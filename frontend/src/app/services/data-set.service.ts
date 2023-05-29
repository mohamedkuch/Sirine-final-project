import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { DataSet } from '../Models/DataSet';

@Injectable({
  providedIn: 'root',
})
export class DataSetService {
  public backendURL = 'http://localhost:8080/api/v1/';

  constructor(private http: HttpClient) {}

  getDataSetList(): Observable<DataSet[]> {
    return this.http.get(this.backendURL + 'datasets').pipe(
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
    let url = this.backendURL + 'datasets/' + id;
    return this.http.get(url);
  }

  setFavoriteDataSet(id: string): Observable<any> {
    let url = this.backendURL + 'users/me/favorites';
    let params = {
      id: id,
    };
    return this.http.post<any>(url, params);
  }

  RemoveFavoriteDataSet(id: string): Observable<any> {
    let url = this.backendURL + 'users/me/favorites';
    let params = {
      id: id,
    };
    return this.http.delete<any>(url, { body: params });
  }
}
