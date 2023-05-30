import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamOkrKeyResultsComponent } from './team-okr-key-results.component';

describe('TeamOkrKeyResultsComponent', () => {
  let component: TeamOkrKeyResultsComponent;
  let fixture: ComponentFixture<TeamOkrKeyResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamOkrKeyResultsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamOkrKeyResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
