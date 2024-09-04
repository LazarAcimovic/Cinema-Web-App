import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Bioskop } from 'src/app/models/bioskop';
import { BioskopService } from 'src/app/services/bioskop.service';
import { BioskopDialogComponent } from '../../dialogs/bioskop-dialog/bioskop-dialog.component';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-bioskop',
  templateUrl: './bioskop.component.html',
  styleUrls: ['./bioskop.component.css'],
})
export class BioskopComponent implements OnInit, OnDestroy {
  displayedColumns = ['id', 'naziv', 'adresa', 'actions'];
  dataSource!: MatTableDataSource<Bioskop>; //izvor podataka koji se koristi za popunjavanje tabele
  subscription!: Subscription;

  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  constructor(private service: BioskopService, public dialog: MatDialog) {}
  ngOnDestroy(): void {
    //vršimo unsubscribe sa strima podataka kako ne bi ostao otvoren
    this.subscription.unsubscribe(); //izvršava se kada se komponenta uništi (kada se komp u browseru više ne prikazuje)
  }

  ngOnInit(): void {
    //izvršava se kada se instancira objekat klase BioskopService
    this.loadData();
  }

  public loadData() {
    (this.subscription = this.service.getAllBioskop().subscribe((data) => {
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
    adresa?: string
  ) {
    const dialogRef = this.dialog.open(BioskopDialogComponent, {
      data: { id, naziv, adresa },
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
