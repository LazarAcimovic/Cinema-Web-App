import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Bioskop } from 'src/app/models/bioskop';
import { Sala } from 'src/app/models/sala';
import { BioskopService } from 'src/app/services/bioskop.service';
import { SalaService } from 'src/app/services/sala.service';

@Component({
  selector: 'app-sala-dialog',
  templateUrl: './sala-dialog.component.html',
  styleUrls: ['./sala-dialog.component.css'],
})
export class SalaDialogComponent implements OnInit {
  //dijalog generalno treba da prihvati podatke i na osnovu njih šalje zahtev za obradu
  flag!: number; //sa ovim govorimo kakvu strukturu dijaloga želimo
  bioskopi!: Bioskop[]; //kada se instancira objekat dijaloga, ovo će biti popunjeno sa svim objektima iz bioskopa
  //i to se koristi za izbor stranog ključa

  constructor(
    public snackBar: MatSnackBar, //snackBar - prozor koji se pojavljuje na stranici
    public dialogRef: MatDialogRef<Sala>,
    @Inject(MAT_DIALOG_DATA) public data: Sala,
    public service: SalaService,
    public bioskopService: BioskopService
  ) {}
  ngOnInit(): void {
    this.bioskopService.getAllBioskop().subscribe((data) => {
      this.bioskopi = data;
    });
  }

  public compare(a: any, b: any) {
    return a.id == b.id;
  }

  public add() {
    this.service.addSala(this.data).subscribe((data) => {
      //console.log(data);                                  nije ni ušao u add
      //data koji smo injektovali
      this.snackBar.open(`Uspesno dodata sala sa id: ${data.id}`, `U redu`, {
        duration: 2500,
      });
    }),
      (error: Error) => {
        console.log(error.name + '' + error.message);
        this.snackBar.open(`Neuspesno dodavanje`, `Zatvori`, {
          duration: 1500,
        });
      };
  }

  public update() {
    this.service.updateSala(this.data).subscribe((data) => {
      this.snackBar.open(
        `Uspesno azurirana sala sa id: ${this.data.id}`,
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
    this.service.deleteSala(this.data.id).subscribe((data) => {
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
