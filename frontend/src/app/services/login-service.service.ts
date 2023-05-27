import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpStatusCode, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  private baseURI:String = "http://localhost:8080/login";
  constructor(private http:HttpClient) { }
  //User
  public loginUser(email:string,password:string): Observable<HttpResponse<any>>{
     return this.http.post<any>(this.baseURI+'/User/'+email+'/'+password,null , {observe:'response'});
  }
  public twoFactorCheckUser(email:string,password:string,twoFactorCode:number):Observable<HttpResponse<any>>{
    return this.http.post<any>(this.baseURI+'/User/verify/'+email+'/'+password+'/'+twoFactorCode,null , {observe:'response'});
  }

  //Admin
  public loginAdmin(email:string,password:string): Observable<HttpResponse<any>>{
    return this.http.post<any>(this.baseURI+'/Admin/'+email+'/'+password,null , {observe:'response'});
  }

  public twoFactorCheckAdmin(email:string,password:string,twoFactorCode:number):Observable<HttpResponse<any>>{
    return this.http.post<any>(this.baseURI+'/Admin/verify/'+email+'/'+password+'/'+twoFactorCode,null , {observe:'response'});
  }

}
