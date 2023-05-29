import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../Models/User';
@Injectable({
  providedIn: 'root',
})
export class RegistrationServiceService {
  private baseURI: String = 'http://localhost:8080/registration';
  constructor(private http: HttpClient) {}

  // User Requests
  public registerUser(user: User) {
    return this.http.post(this.baseURI + '/createUser', user, {
      responseType: 'text',
    });
  }

  public getUserList() {
    return this.http.get<User[]>(this.baseURI + '/listUser');
  }
}
