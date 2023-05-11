import { Component } from '@angular/core';
import {LoginServiceService} from "../../services/login-service.service";

@Component({
  selector: 'app-login-window',
  templateUrl: './login-window.component.html',
  styleUrls: ['./login-window.component.scss']
})
export class LoginWindowComponent {

  email!:string;
  password!:string;
  twoFactorCode:string[];

  isUser:boolean = true;
  validateLogin: boolean = false;
  constructor(private loginService:LoginServiceService ) {
    this.email = "";
    this.password = "";
    this.twoFactorCode = [];
  }

  toggleLogin(){
    if(!this.checkInputData()) return;
    this.login()
  }

  toggleTwoFactor(){
    let input_code:string = "";
    for (let i = 0; i < this.twoFactorCode.length; i++) {
      input_code+=this.twoFactorCode[i];
    }
  }

  checkInputData():boolean{
    let bool: boolean = true;
    console.log(this.email);
    console.log(this.password);
    if( !(this.email.indexOf("@") >= 0) || !(this.email.indexOf(".") >= 0) ){
      alert("email nicht Korrekt angegeben.");
      bool = false;
    }
    if(!bool) this.validateLogin = false;
    return bool;
  }

  login(){
    this.validateLogin = true; //muss noch geändert werden
  }

  setAdminLogin(){
    this.isUser=false;
  }
  setUserLogin(){
    this.isUser = true;
  }

}
