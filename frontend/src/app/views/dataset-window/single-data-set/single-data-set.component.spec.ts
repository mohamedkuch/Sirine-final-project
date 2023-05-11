import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleDataSetComponent } from './single-data-set.component';

describe('SingleDataSetComponent', () => {
  let component: SingleDataSetComponent;
  let fixture: ComponentFixture<SingleDataSetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SingleDataSetComponent]
    });
    fixture = TestBed.createComponent(SingleDataSetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
