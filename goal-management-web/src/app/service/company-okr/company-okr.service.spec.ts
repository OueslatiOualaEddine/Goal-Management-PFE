import { TestBed } from '@angular/core/testing';

import { CompanyOKRService } from './company-okr.service';

describe('CompanyOKRService', () => {
  let service: CompanyOKRService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyOKRService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
