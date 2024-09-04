import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Film } from '../models/film';
import { FILM_URL } from '../constants';

@Injectable({
  providedIn: 'root',
})
export class FilmService {
  constructor(private httpClient: HttpClient) {} //govorimo kakav dependency injection nam treba

  public getAllFilm(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/Film'); //konstruišemo http zahtev koristeći httpClient
  }

  public addFilm(film: Film): Observable<any> {
    //bioskop: Bioskop- parametar kao request body,  Observable<any> - povratni tip
    return this.httpClient.post(`${FILM_URL}`, film);
  }

  public updateFilm(film: Film): Observable<any> {
    return this.httpClient.put(`${FILM_URL}/id/${film.id}`, film);
  }

  public deleteFilm(filmID: number): Observable<any> {
    return this.httpClient.delete(`${FILM_URL}/id/${filmID}`, {
      responseType: 'text',
    });
  }
}
