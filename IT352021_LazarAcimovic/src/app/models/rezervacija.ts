import { Film } from './film';
import { Sala } from './sala';

export class Rezervacija {
  id!: number;
  broj_osoba!: number;
  cena_karte!: number;
  datum!: Date;
  placeno!: boolean;
  film!: Film;
  sala!: Sala;
}
