import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Bioskop } from 'src/app/models/bioskop';
import { Sala } from 'src/app/models/sala';
import { SalaService } from 'src/app/services/sala.service';
import { SalaDialogComponent } from '../../dialogs/sala-dialog/sala-dialog.component';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-sala',
  templateUrl: './sala.component.html',
  styleUrls: ['./sala.component.css'],
})
export class SalaComponent implements OnInit, OnDestroy {
  displayedColumns = ['id', 'kapacitet', 'broj_redova', 'bioskop', 'actions'];
  dataSource!: MatTableDataSource<Sala>; //izvor podataka koji se koristi za popunjavanje tabele
  subscription!: Subscription;
  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  parentSelectedSala!: Sala;

  constructor(private service: SalaService, public dialog: MatDialog) {}
  ngOnDestroy(): void {
    //vršimo unsubscribe sa strima podataka kako ne bi ostao otvoren
    this.subscription.unsubscribe(); //izvršava se kada se komponenta uništi (kada se komp u browseru više ne prikazuje)
  }

  ngOnInit(): void {
    //izvršava se kada se instancira objekat klase BioskopService
    this.loadData();
  }

  public loadData() {
    (this.subscription = this.service.getAllSala().subscribe((data) => {
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
    kapacitet?: number,
    broj_redova?: number,
    bioskop?: Bioskop
  ) {
    const dialogRef = this.dialog.open(SalaDialogComponent, {
      data: { id, kapacitet, broj_redova, bioskop },
    });
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe((result) => {
      if (result == 1) {
        this.loadData();
      }
    });
  }

  public selectRow(row: Sala) {
    this.parentSelectedSala = row; //poziva se kada kliknemo na neki red u tabeli
  }

  public applyFilter(filter: any) {
    filter = filter.target.value;
    filter = filter.trim();
    filter = filter.toLocaleLowerCase();
    this.dataSource.filter = filter;
  }
}
