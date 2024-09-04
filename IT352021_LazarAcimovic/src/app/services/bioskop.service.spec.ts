import { TestBed } from '@angular/core/testing';

import { BioskopService } from './bioskop.service';

describe('BioskopService', () => {
  let service: BioskopService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BioskopService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
