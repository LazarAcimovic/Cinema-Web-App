package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

	List<Film> findByNazivContainingIgnoreCase(String naziv);
}
