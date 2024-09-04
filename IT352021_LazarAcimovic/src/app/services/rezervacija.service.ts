import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { REZERVACIJA_URL, SALE_BY_REZERVACIJA_URL } from '../constants';
import { Rezervacija } from '../models/rezervacija';

@Injectable({
  providedIn: 'root',
})
export class RezervacijaService {
  constructor(private httpClient: HttpClient) {} //govorimo kakav dependency injection nam treba

  public getRezervacijeBySala(salaID: number): Observable<any> {
    return this.httpClient.get(`${SALE_BY_REZERVACIJA_URL}/${salaID}`);
  }

  public getAllRezervacija(): Observable<any> {
    return this.httpClient.get(`${REZERVACIJA_URL}`); //konstruišemo http zahtev koristeći httpClient
  }

  public addRezervacija(rezervacija: Rezervacija): Observable<any> {
    //bioskop: Bioskop- parametar kao request body,  Observable<any> - povratni tip
    return this.httpClient.post(`${REZERVACIJA_URL}`, rezervacija);
  }

  public updateRezervacija(rezervacija: Rezervacija): Observable<any> {
    return this.httpClient.put(
      `${REZERVACIJA_URL}/id/${rezervacija.id}`,
      rezervacija
    );
  }

  public deleteRezervacija(rezervacijaID: number): Observable<any> {
    return this.httpClient.delete(`${REZERVACIJA_URL}/id/${rezervacijaID}`, {
      responseType: 'text',
    });
  }
}
