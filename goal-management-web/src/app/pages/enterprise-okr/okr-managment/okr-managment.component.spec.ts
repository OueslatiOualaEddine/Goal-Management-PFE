import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OkrManagmentComponent } from './okr-managment.component';

describe('OkrManagmentComponent', () => {
  let component: OkrManagmentComponent;
  let fixture: ComponentFixture<OkrManagmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OkrManagmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OkrManagmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
