import { Component, OnInit } from '@angular/core';
import { CSVService } from '../../testService/CSVService';
import { TestCSV } from '../../Models/testCSV';

import { RegistrationServiceService } from '../../../services/registration-service.service';
import { User } from '../../../Models/User';

@Component({
  selector: 'app-test-window',
  templateUrl: './test-window.component.html',
  styleUrls: ['./test-window.component.css'],
})
export class TestWindowComponent {
  csvList: TestCSV[] | any;
  constructor(
    private csvService: CSVService,
    private registrationService: RegistrationServiceService
  ) {}
  public updateCSVLIst() {
    this.csvService.getCSVlist().subscribe((list) => (this.csvList = list));
  }

  ngOnInit(): void {
    this.updateCSVLIst();
    this.updateUserList();
  }

  userList: User[] | any;

  public updateUserList() {
    this.registrationService
      .getUserList()
      .subscribe((list) => (this.userList = list));
  }
}
