package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Film;

@Service
public interface FilmService extends CrudService<Film> {

	List<Film> getFilmByNaziv(String naziv);
}
