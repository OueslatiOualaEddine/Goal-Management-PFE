import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamOKRComponent } from './team-okr.component';

describe('TeamOKRComponent', () => {
  let component: TeamOKRComponent;
  let fixture: ComponentFixture<TeamOKRComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamOKRComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamOKRComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
