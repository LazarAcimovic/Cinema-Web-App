package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rva.models.Film;
import rva.models.Rezervacija;
import rva.models.Sala;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {

	List<Rezervacija> findByPlacenoEquals(boolean placeno);
	
	List<Rezervacija> findBySala(Sala sala);
	List<Rezervacija> findByFilm(Film film);
}
