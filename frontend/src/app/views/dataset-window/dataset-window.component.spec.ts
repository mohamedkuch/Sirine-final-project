import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatasetWindowComponent } from './dataset-window.component';

describe('DatasetWindowComponent', () => {
  let component: DatasetWindowComponent;
  let fixture: ComponentFixture<DatasetWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DatasetWindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DatasetWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
