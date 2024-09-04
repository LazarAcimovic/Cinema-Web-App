import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BioskopComponent } from './components/main/bioskop/bioskop.component';
import { SalaComponent } from './components/main/sala/sala.component';
import { FilmComponent } from './components/main/film/film.component';
import { RezervacijaComponent } from './components/main/rezervacija/rezervacija.component';
import { HomeComponent } from './components/utility/home/home.component';
import { AboutComponent } from './components/utility/about/about.component';
import { AuthorComponent } from './components/utility/author/author.component';

const routes: Routes = [
  { path: 'bioskop', component: BioskopComponent },
  { path: 'sala', component: SalaComponent },
  { path: 'film', component: FilmComponent },
  { path: 'rezervacija', component: RezervacijaComponent },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'author', component: AuthorComponent },
  { path: '', component: HomeComponent, pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
