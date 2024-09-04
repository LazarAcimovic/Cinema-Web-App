import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmDialogComponent } from './film-dialog.component';

describe('FilmDialogComponent', () => {
  let component: FilmDialogComponent;
  let fixture: ComponentFixture<FilmDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FilmDialogComponent]
    });
    fixture = TestBed.createComponent(FilmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
