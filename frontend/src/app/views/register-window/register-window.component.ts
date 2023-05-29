import { Component, OnInit } from '@angular/core';
import { RegistrationServiceService } from '../../services/registration-service.service';

import { User } from '../../Models/User';
import { last } from 'rxjs';
@Component({
  selector: 'app-register-window',
  templateUrl: './register-window.component.html',
  styleUrls: ['./register-window.component.css'],
})
export class RegisterWindowComponent {
  //Data coming in
  vorname!: string;
  nachname!: string;
  email!: string;
  passwort!: string;
  passwort_wiederholt!: string;
  geburtstag!: string;
  profilbild!: string;

  user: User;

  isUser: boolean = true;

  registrationMSG: string = '';

  constructor(private registrationService: RegistrationServiceService) {
    this.user = new User();
  }

  toggleRegistration() {
    if (!this.checkInputData()) return;
    this.assignAttributesToAccountType();
    this.register();
  }

  checkInputData(): boolean {
    let bool: boolean = true;

    if (!(this.email.indexOf('@') >= 0) || !(this.email.indexOf('.') >= 0)) {
      alert('email nicht Korrekt angegeben.');
      bool = false;
    }
    if (this.passwort.toString() != this.passwort_wiederholt.toString()) {
      alert('passwörter stimmen nicht überein.');
      bool = false;
    }

    return bool;
  }

  assignAttributesToAccountType() {
    this.user.firstname = this.vorname;
    this.user.lastname = this.nachname;
    this.user.email = this.email;
    this.user.profilePicture = this.profilbild;
  }
  register() {
    // Zugriff auf Backend durch RegistrationService
    if (this.isUser) {
      //User Registration
      this.registrationService
        .registerUser(this.user)
        .subscribe((text) => (this.registrationMSG = text));
    } else {
      //Admin Registration
    }
  }

  setAdminRegistration() {
    this.isUser = false;
  }
  setUserRegistration() {
    this.isUser = true;
  }

  protected readonly last = last;

  ngOnInit(): void {}
}
