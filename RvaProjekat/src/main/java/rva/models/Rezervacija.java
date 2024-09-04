package rva.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Rezervacija implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "REZERVACIJA_SEQ_GENERATOR", sequenceName = "REZERVACIJA_SEQ",
	allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
	generator = "REZERVACIJA_SEQ_GENERATOR")
	private int id;
	
	
	private int broj_osoba;
	private double cena_karte;
	private Date datum;
	private boolean placeno;
	
	
	
	
	
	@ManyToOne
	@JoinColumn(name="film")
	private Film film;
	
	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	@ManyToOne
	 //da nam ne bi prikazivao nazubljene podatke
	@JoinColumn(name="sala")
	private Sala sala;
	
	public Rezervacija() {
		
	}

	public Rezervacija(int id, int broj_osoba, double cena_karte, Date datum, boolean placeno, int film, int sala) {
		
		this.id = id;
		this.broj_osoba = broj_osoba;
		this.cena_karte = cena_karte;
		this.datum = datum;
		this.placeno = placeno;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBroj_osoba() {
		return broj_osoba;
	}

	public void setBroj_osoba(int broj_osoba) {
		this.broj_osoba = broj_osoba;
	}

	public double getCena_karte() {
		return cena_karte;
	}

	public void setCena_karte(double cena_karte) {
		this.cena_karte = cena_karte;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public boolean isPlaceno() {
		return placeno;
	}

	public void setPlaceno(boolean placeno) {
		this.placeno = placeno;
	}

	
	
	
	
	
	

}
