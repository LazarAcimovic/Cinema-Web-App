package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Film implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "FILM_SEQ_GENERATOR", sequenceName = "FILM_SEQ",
	allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
	generator = "FILM_SEQ_GENERATOR")
	private int id;
	
	
	
	private String naziv;
	private int recenzija;
	private int trajanje;
	private String zanr;
	
	@JsonIgnore
	@OneToMany(mappedBy = "film", cascade=CascadeType.REMOVE)
	private List<Rezervacija> rezervacije;
	
	public List<Rezervacija> getRezervacije() {
		return rezervacije;
	}
	public void setRezervacije(List<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}
	public Film() {
		
	}
	public Film(int id, String naziv, int recenzija, int trajanje, String zanr) {
		
		this.id = id;
		this.naziv = naziv;
		this.recenzija = recenzija;
		this.trajanje = trajanje;
		this.zanr = zanr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getRecenzija() {
		return recenzija;
	}
	public void setRecenzija(int recenzija) {
		this.recenzija = recenzija;
	}
	public int getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	public String getZanr() {
		return zanr;
	}
	public void setZanr(String zanr) {
		this.zanr = zanr;
	}
	
	
	
}

