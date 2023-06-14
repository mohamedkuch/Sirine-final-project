import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GeoDataMapService {
  private baseURI: string = "http://localhost:8080/geoData"

  constructor(private http: HttpClient) {}

  public getGeoDatasets() {}

  public uploadGeoData(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post<any>(this.baseURI + "/uploadFile", formData);
  }
}
