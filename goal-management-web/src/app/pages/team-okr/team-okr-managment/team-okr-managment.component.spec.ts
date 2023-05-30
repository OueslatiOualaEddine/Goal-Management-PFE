import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamOkrManagmentComponent } from './team-okr-managment.component';

describe('TeamOkrManagmentComponent', () => {
  let component: TeamOkrManagmentComponent;
  let fixture: ComponentFixture<TeamOkrManagmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamOkrManagmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamOkrManagmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
