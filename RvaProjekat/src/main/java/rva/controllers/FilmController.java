package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.models.Bioskop;
import rva.models.Film;
import rva.services.FilmService;

@RestController
public class FilmController {

	@Autowired
	private FilmService service;
	
	@GetMapping("/Film")
	public List<Film> getAllFilm(){
		return service.getAll();
	}
	
	@GetMapping("/Film/id/{id}")
	public ResponseEntity<?> getFilmById(@PathVariable int id){
		Optional<Film> film = service.findById(id);
		if(film.isPresent()) {
			return ResponseEntity.ok(film.get());		
	}
	return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist");
	}
	
	@GetMapping("/Film/naziv/{naziv}")
	public ResponseEntity<?> getFilmByNaziv(@PathVariable String naziv){
		List<Film> film = service.getFilmByNaziv(naziv);
		if(film.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with Naziv:  "+naziv + " doesn not exist" ); 
		}
		return ResponseEntity.ok(film);
	}
	
	
	@PostMapping("/Film")
	public ResponseEntity<?> createFilm(@RequestBody Film film){
		if(service.existsById(film.getId())) {
			return ResponseEntity.status(409).body("Resurce already exists");
		}
		Film savedFilm = service.create(film);
		URI uri = URI.create("/Film/id/"+ savedFilm.getId());
		return ResponseEntity.created(uri).body(savedFilm);
	}
	
	@PutMapping("/Film/id/{id}")
	public ResponseEntity<?> updateFilm (@RequestBody Film film, @PathVariable int id)
	{
		Optional<Film> updatedFilm = service.update(film, id);
		if(updatedFilm.isPresent()) {
			return ResponseEntity.ok(updatedFilm.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be updated because it doesn't exist" );
	}
	
	@DeleteMapping("/Film/id/{id}")
	public ResponseEntity<?> deleteFilm(@PathVariable int id){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been " +
					"successfully deleted");
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be deleted because it doesn't exist" );
	}
	 
	
	
	
}
