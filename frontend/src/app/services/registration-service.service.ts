import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Admin } from "../Models/Admin";
import { User } from "../Models/User";
@Injectable({
  providedIn: 'root'
})
export class RegistrationServiceService {
  private baseURI:String = "http://localhost:8080/registration";
  constructor(private http:HttpClient) { }

  // Admin requests
  public registerAdmin(admin:Admin){
    return this.http.post(this.baseURI+'/createAdmin', admin,  {responseType: 'text'});
  }

  public getAdminList(){
    return this.http.get<Admin[]>(this.baseURI+'/listAdmin');
  }


  // User Requests
  public registerUser(user: User){
    return this.http.post(this.baseURI+'/createUser', user,  {responseType: 'text'})
  }

  public getUserList() {
    return this.http.get<User[]>(this.baseURI+'/listUser');
  }
}
