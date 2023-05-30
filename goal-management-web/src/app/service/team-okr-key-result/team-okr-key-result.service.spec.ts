import { TestBed } from '@angular/core/testing';

import { TeamOkrKeyResultService } from './team-okr-key-result.service';

describe('TeamOkrKeyResultService', () => {
  let service: TeamOkrKeyResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamOkrKeyResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
