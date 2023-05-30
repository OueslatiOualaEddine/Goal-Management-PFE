import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamsManagmentComponent } from './teams-managment.component';

describe('TeamsManagmentComponent', () => {
  let component: TeamsManagmentComponent;
  let fixture: ComponentFixture<TeamsManagmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamsManagmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamsManagmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
