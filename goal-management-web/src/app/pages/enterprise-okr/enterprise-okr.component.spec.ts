import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnterpriseOKRComponent } from './enterprise-okr.component';

describe('EnterpriseOKRComponent', () => {
  let component: EnterpriseOKRComponent;
  let fixture: ComponentFixture<EnterpriseOKRComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EnterpriseOKRComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EnterpriseOKRComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
