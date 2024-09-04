import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FILM_URL, SALA_URL } from '../constants';
import { Sala } from '../models/sala';

@Injectable({
  providedIn: 'root',
})
export class SalaService {
  constructor(private httpClient: HttpClient) {} //govorimo kakav dependency injection nam treba

  public getAllSala(): Observable<any> {
    return this.httpClient.get(`${SALA_URL}`); //konstruišemo http zahtev koristeći httpClient
  }

  public addSala(sala: Sala): Observable<any> {
    //bioskop: Bioskop- parametar kao request body,  Observable<any> - povratni tip
    return this.httpClient.post(`${SALA_URL}`, sala);
  }

  public updateSala(sala: Sala): Observable<any> {
    return this.httpClient.put(`${SALA_URL}/id/${sala.id}`, sala);
  }

  public deleteSala(salaID: number): Observable<any> {
    return this.httpClient.delete(`${SALA_URL}/id/${salaID}`, {
      responseType: 'text',
    });
  }
}
