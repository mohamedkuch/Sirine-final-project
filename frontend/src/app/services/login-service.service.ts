import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  public backendURL = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient) {}

  public loginUser(
    email: string,
    password: string
  ): Observable<HttpResponse<any>> {
    const requestBody = {
      email: email,
      password: password,
    };

    let url = this.backendURL + '/login';

    return this.http.post<any>(url, requestBody);
  }

  public loggedIn() {
    return !!localStorage.getItem('token');
  }

  public getToken() {
    return localStorage.getItem('token');
  }
  // public twoFactorCheckUser(
  //   email: string,
  //   password: string,
  //   twoFactorCode: number
  // ): Observable<HttpResponse<any>> {
  //   return this.http.post<any>(
  //     this.baseURI +
  //       '/User/verify/' +
  //       email +
  //       '/' +
  //       password +
  //       '/' +
  //       twoFactorCode,
  //     null,
  //     { observe: 'response' }
  //   );
  // }

  // //Admin
  // public loginAdmin(
  //   email: string,
  //   password: string
  // ): Observable<HttpResponse<any>> {
  //   return this.http.post<any>(
  //     this.baseURI + '/Admin/' + email + '/' + password,
  //     null,
  //     { observe: 'response' }
  //   );
  // }

  // public twoFactorCheckAdmin(
  //   email: string,
  //   password: string,
  //   twoFactorCode: number
  // ): Observable<HttpResponse<any>> {
  //   return this.http.post<any>(
  //     this.baseURI +
  //       '/Admin/verify/' +
  //       email +
  //       '/' +
  //       password +
  //       '/' +
  //       twoFactorCode,
  //     null,
  //     { observe: 'response' }
  //   );
  // }
}
