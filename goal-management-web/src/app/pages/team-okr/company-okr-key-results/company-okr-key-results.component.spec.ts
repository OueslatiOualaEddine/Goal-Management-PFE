import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyOkrKeyResultsComponent } from './company-okr-key-results.component';

describe('CompanyOkrKeyResultsComponent', () => {
  let component: CompanyOkrKeyResultsComponent;
  let fixture: ComponentFixture<CompanyOkrKeyResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompanyOkrKeyResultsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompanyOkrKeyResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
