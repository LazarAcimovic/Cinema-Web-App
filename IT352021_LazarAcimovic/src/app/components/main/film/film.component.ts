import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Film } from 'src/app/models/film';
import { FilmService } from 'src/app/services/film.service';
import { FilmDialogComponent } from '../../dialogs/film-dialog/film-dialog.component';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-film',
  templateUrl: './film.component.html',
  styleUrls: ['./film.component.css'],
})
export class FilmComponent implements OnInit, OnDestroy {
  displayedColumns = [
    'id',
    'naziv',
    'recenzija',
    'trajanje',
    'zanr',
    'actions',
  ];
  dataSource!: MatTableDataSource<Film>; //izvor podataka koji se koristi za popunjavanje tabele
  subscription!: Subscription;
  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  constructor(private service: FilmService, public dialog: MatDialog) {}
  ngOnDestroy(): void {
    //vršimo unsubscribe sa strima podataka kako ne bi ostao otvoren
    this.subscription.unsubscribe(); //izvršava se kada se komponenta uništi (kada se komp u browseru više ne prikazuje)
  }

  ngOnInit(): void {
    //izvršava se kada se instancira objekat klase BioskopService
    this.loadData();
  }

  public loadData() {
    (this.subscription = this.service.getAllFilm().subscribe((data) => {
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
    naziv?: string,
    recenzija?: number,
    trajanje?: number,
    zanr?: string
  ) {
    const dialogRef = this.dialog.open(FilmDialogComponent, {
      data: { id, naziv, recenzija, trajanje, zanr },
    });
    dialogRef.componentInstance.flag = flag;
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
