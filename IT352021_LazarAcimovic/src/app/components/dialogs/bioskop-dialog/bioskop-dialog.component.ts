import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Bioskop } from 'src/app/models/bioskop';
import { BioskopService } from 'src/app/services/bioskop.service';

@Component({
  selector: 'app-bioskop-dialog',
  templateUrl: './bioskop-dialog.component.html',
  styleUrls: ['./bioskop-dialog.component.css'],
})
export class BioskopDialogComponent {
  //dijalog generalno treba da prihvati podatke i na osnovu njih šalje zahtev za obradu
  flag!: number; //sa ovim govorimo kakvu strukturu dijaloga želimo

  constructor(
    public snackBar: MatSnackBar, //snackBar - prozor koji se pojavljuje na stranici
    public dialogRef: MatDialogRef<Bioskop>,
    @Inject(MAT_DIALOG_DATA) public data: Bioskop,
    public service: BioskopService
  ) {}

  public add() {
    this.service.addBioskop(this.data).subscribe((data) => {
      //data koji smo injektovali
      this.snackBar.open(
        `Uspesno dodat bioskop sa nazivom: ${data.naziv}`,
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
    this.service.updateBioskop(this.data).subscribe((data) => {
      this.snackBar.open(
        `Uspesno azuriran bioskop sa nazivom: ${this.data.naziv}`,
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
    this.service.deleteBioskop(this.data.id).subscribe((data) => {
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
