import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  private baseURI:String = "http://localhost:8080";
  constructor(private http:HttpClient) { }


  public getBackendText(){
    return this.http.get(this.baseURI+'/test/text',{responseType: 'text'});
  }
  
}
