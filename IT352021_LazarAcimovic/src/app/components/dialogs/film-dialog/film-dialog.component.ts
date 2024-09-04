import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Film } from 'src/app/models/film';
import { FilmService } from 'src/app/services/film.service';

@Component({
  selector: 'app-film-dialog',
  templateUrl: './film-dialog.component.html',
  styleUrls: ['./film-dialog.component.css'],
})
export class FilmDialogComponent {
  //dijalog generalno treba da prihvati podatke i na osnovu njih šalje zahtev za obradu
  flag!: number; //sa ovim govorimo kakvu strukturu dijaloga želimo

  constructor(
    public snackBar: MatSnackBar, //snackBar - prozor koji se pojavljuje na stranici
    public dialogRef: MatDialogRef<Film>,
    @Inject(MAT_DIALOG_DATA) public data: Film,
    public service: FilmService
  ) {}

  public add() {
    this.service.addFilm(this.data).subscribe((data) => {
      //data koji smo injektovali
      this.snackBar.open(
        `Uspesno dodat film sa nazivom: ${data.naziv}`,
        `U redu`,
        { duration: 2500 }
      );
    }),
      (error: Error) => {
        console.log(error.name + '' + error.message);
        this.snackBar.open(`Neuspesno dodavanje`, `Zatvori`, {
          duration: 1500,
        });
      };
  }

  public update() {
    this.service.updateFilm(this.data).subscribe((data) => {
      this.snackBar.open(
        `Uspesno azuriran film sa nazivom: ${this.data.naziv}`,
        `U redu`,
        { duration: 2500 }
      );
    }),
      (error: Error) => {
        console.log(error.name + '' + error.message);
        this.snackBar.open(`Neuspesno azuriranje`, `Zatvori`, {
          duration: 1500,
        });
      };
  }

  public delete() {
    this.service.deleteFilm(this.data.id).subscribe((data) => {
      this.snackBar.open(`${data}`, `U redu`, { duration: 2500 });
    }),
      (error: Error) => {
        console.log(error.name + '' + error.message);
        this.snackBar.open(`Neuspesno brisanje`, `Zatvori`, {
          duration: 1500,
        });
      };
  }

  public cancel() {
    this.dialogRef.close();
    this.snackBar.open(`Odustali ste od izmena`, `Zatvori`, { duration: 1500 });
  }
}
