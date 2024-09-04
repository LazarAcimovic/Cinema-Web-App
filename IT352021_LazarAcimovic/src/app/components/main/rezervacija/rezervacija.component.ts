import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Film } from 'src/app/models/film';
import { Rezervacija } from 'src/app/models/rezervacija';
import { Sala } from 'src/app/models/sala';
import { RezervacijaService } from 'src/app/services/rezervacija.service';
import { RezervacijaDialogComponent } from '../../dialogs/rezervacija-dialog/rezervacija-dialog.component';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-rezervacija',
  templateUrl: './rezervacija.component.html',
  styleUrls: ['./rezervacija.component.css'],
})
export class RezervacijaComponent implements OnInit, OnDestroy, OnChanges {
  displayedColumns = [
    'id',
    'broj_osoba',
    'cena_karte',
    'datum',
    'placeno',

    'film',
    'actions',
  ];
  dataSource!: MatTableDataSource<Rezervacija>; //izvor podataka koji se koristi za popunjavanje tabele
  subscription!: Subscription;
  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  @Input() childSelectedSala!: Sala; //input- omogućava korišćenje property binding-a

  constructor(private service: RezervacijaService, public dialog: MatDialog) {}
  ngOnChanges(changes: SimpleChanges): void {
    this.loadData(); //trigeruje se kada se menja vr. propertija chilSelectedSala
  }
  ngOnDestroy(): void {
    //vršimo unsubscribe sa strima podataka kako ne bi ostao otvoren
    this.subscription.unsubscribe(); //izvršava se kada se komponenta uništi (kada se komp u browseru više ne prikazuje)
  }

  ngOnInit(): void {
    //izvršava se kada se instancira objekat klase BioskopService
    this.loadData();
  }

  public loadData() {
    (this.subscription = this.service
      .getRezervacijeBySala(this.childSelectedSala.id)
      .subscribe((data) => {
        //kupi podatke sa backenda
        // console.log(data);
        this.dataSource = new MatTableDataSource(data); //storujemo podatke koje dobijamo sa backenda
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      })),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
      };
  }

  public openDialog(
    flag: number,
    id?: number,
    broj_osoba?: number,
    cena_karte?: number,
    datum?: Date,
    placeno?: boolean,

    sala?: Sala
  ) {
    const dialogRef = this.dialog.open(RezervacijaDialogComponent, {
      data: { id, broj_osoba, cena_karte, datum, placeno, sala },
    });
    dialogRef.componentInstance.flag = flag;
    dialogRef.componentInstance.data.sala = this.childSelectedSala; // novo
    dialogRef.afterClosed().subscribe((result) => {
      if (result == 1) {
        this.loadData();
      }
    });
  }

  public applyFilter(filter: any) {
    filter = filter.target.value;
    filter = filter.trim();
    filter = filter.toLocaleLowerCase();
    this.dataSource.filter = filter;
  }
}
