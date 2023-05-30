import { TestBed } from '@angular/core/testing';

import { TeamOkrService } from './team-okr.service';

describe('TeamOkrService', () => {
  let service: TeamOkrService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamOkrService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
