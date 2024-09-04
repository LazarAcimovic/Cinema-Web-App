import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatExpansionModule } from '@angular/material/expansion';
import { BioskopComponent } from './components/main/bioskop/bioskop.component';
import { RezervacijaComponent } from './components/main/rezervacija/rezervacija.component';
import { SalaComponent } from './components/main/sala/sala.component';
import { FilmComponent } from './components/main/film/film.component';
import { HomeComponent } from './components/utility/home/home.component';
import { AboutComponent } from './components/utility/about/about.component';
import { AuthorComponent } from './components/utility/author/author.component';
import { HttpClientModule } from '@angular/common/http';

import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BioskopDialogComponent } from './components/dialogs/bioskop-dialog/bioskop-dialog.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { FilmDialogComponent } from './components/dialogs/film-dialog/film-dialog.component';
import { SalaDialogComponent } from './components/dialogs/sala-dialog/sala-dialog.component';
import { RezervacijaDialogComponent } from './components/dialogs/rezervacija-dialog/rezervacija-dialog.component';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  declarations: [
    AppComponent,
    BioskopComponent,
    RezervacijaComponent,
    SalaComponent,
    FilmComponent,
    HomeComponent,
    AboutComponent,
    AuthorComponent,
    BioskopDialogComponent,
    FilmDialogComponent,
    SalaDialogComponent,
    RezervacijaDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatGridListModule,
    MatExpansionModule,
    HttpClientModule,
    MatTableModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatCheckboxModule,
    MatSortModule,
    MatPaginatorModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
