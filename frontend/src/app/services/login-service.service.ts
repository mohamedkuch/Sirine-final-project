import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { User } from '../Models/User';
import { UserRole } from '../Models/Role';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  public backendURL = 'http://localhost:8080/api/v1/';

  constructor(private http: HttpClient) {}

  public loginUser(
    email: string,
    password: string
  ): Observable<HttpResponse<any>> {
    const requestBody = {
      email: email,
      password: password,
    };

    let url = this.backendURL + 'auth/login';

    return this.http.post<any>(url, requestBody);
  }

  public logout(): void {
    localStorage.removeItem('token');
  }

  public loggedIn() {
    return !!localStorage.getItem('token');
  }

  public getToken() {
    return localStorage.getItem('token');
  }

  public getCurrentUser(): Observable<User> {
    let url = this.backendURL + 'users/me';
    return this.http.get(url).pipe(
      map((data: any) => {
        const user: User = {
          id: data.id,
          firstname: data.firstname,
          lastname: data.lastname,
          email: data.email,
          birthday: new Date(data.birthday).toDateString(),
          profilePicture: data.profilePicture,
          address: data.address,
          phone: data.phone,
          role: data.role === 'USER' ? UserRole.USER : UserRole.ADMIN,
        };
        return user;
      })
    );
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
