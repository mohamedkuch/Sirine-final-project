import { Component, OnInit} from '@angular/core';
import {LoginServiceService} from "../../services/login-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent {

  loggedIn:boolean;
  constructor(private loginservice:LoginServiceService, private router:Router) {
    this.loggedIn=this.loginservice.isLoggedIn();
  }
  ngOnInit():void{

  }

  public logout(){
    this.loginservice.logout().subscribe(response =>{
        alert(response.body);
      },
      error=>{
        //TODO: ALLE Responses werden als "error" erkannt...
      });
    this.loggedIn=this.loginservice.isLoggedIn();
  }

  public login(){
    this.router.navigate(['/login'],{replaceUrl:true})
    this.loggedIn=this.loginservice.isLoggedIn(); //hier evt unnötig
  }

}
