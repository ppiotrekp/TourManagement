import { TestBed } from '@angular/core/testing';

import { MyHistoryService } from './my-history.service';

describe('MyHistoryService', () => {
  let service: MyHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
