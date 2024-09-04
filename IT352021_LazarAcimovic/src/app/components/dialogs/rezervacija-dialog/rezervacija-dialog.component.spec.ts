import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RezervacijaDialogComponent } from './rezervacija-dialog.component';

describe('RezervacijaDialogComponent', () => {
  let component: RezervacijaDialogComponent;
  let fixture: ComponentFixture<RezervacijaDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RezervacijaDialogComponent]
    });
    fixture = TestBed.createComponent(RezervacijaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
