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
import rva.models.Rezervacija;
import rva.models.Sala;
import rva.services.FilmService;
import rva.services.RezervacijaService;
import rva.services.SalaService;

@RestController
public class RezervacijaController {
	
	
	
	@Autowired
	private RezervacijaService service;
	
	@Autowired
	private FilmService filmService;
	
	@Autowired
	private SalaService salaService;
	

	@Autowired
	private SalaService sala;
	
	@Autowired
	private FilmService film;
	
	@GetMapping("/Rezervacija")
	public List<Rezervacija> getAllRezervacija(){
		return service.getAll();
	}
	
	@GetMapping("/Rezervacija/id/{id}")
	public ResponseEntity<?> getRezervacijaById(@PathVariable int id){
		Optional<Rezervacija> rezervacija = service.findById(id);
		if(rezervacija.isPresent()) {
			return ResponseEntity.ok(rezervacija.get());		
	}
	return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist");
	}
	
	@GetMapping("/Rezervacija/naziv/{placeno}")
	public ResponseEntity<?> getRezervacijaByNaziv(@PathVariable boolean placeno){
		List<Rezervacija> rezervacija = service.getRezervacijaByPlaceno(placeno);
		if(rezervacija.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with placeno:  "+placeno + " doesn not exist" ); 
		}
		return ResponseEntity.ok(rezervacija);
	}
	
	
	@PostMapping("/Rezervacija")
	public ResponseEntity<?> createRezervacija(@RequestBody Rezervacija rezervacija){
		if(service.existsById(rezervacija.getId())) {
			return ResponseEntity.status(409).body("Resurce already exists");
		}
		Rezervacija savedRezervacija = service.create(rezervacija);
		URI uri = URI.create("/Rezervacija/id/"+ savedRezervacija.getId());
		return ResponseEntity.created(uri).body(savedRezervacija);
	}
	
	@PutMapping("/Rezervacija/id/{id}")
	public ResponseEntity<?> updateRezervacija (@RequestBody Rezervacija rezervacija, @PathVariable int id)
	{
		Optional<Rezervacija> updatedRezervacija = service.update(rezervacija, id);
		if(updatedRezervacija.isPresent()) {
			return ResponseEntity.ok(updatedRezervacija.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be updated because it doesn't exist" );
	}
	 
	
	
	@GetMapping("/Rezervacija/film/{foreignKey}")
	public ResponseEntity<?> getRezervacijaByFilm(@PathVariable int foreignKey){
		Optional<Film> film = filmService.findById(foreignKey);
			if(film.isPresent()) {
				List<Rezervacija> rezervacije = service.findByForeignKey(film.get());
				if(rezervacije.isEmpty()) {
					return ResponseEntity.status(404).body("Resources with foreign key: " +
							foreignKey + " do not exist");
				}else {
					return ResponseEntity.ok(rezervacije);
				}
				
			}
			return ResponseEntity.status(400).body("Invalid foreign key: " + foreignKey);
	}
	
	
	@GetMapping("/Rezervacija/sala/{foreignKey}")
	public ResponseEntity<?> getRezervacijaBySala(@PathVariable int foreignKey){
		Optional<Sala> sala = salaService.findById(foreignKey);
			if(sala.isPresent()) {
				List<Rezervacija> rezervacije = service.findByForeignKey(sala.get());
				if(rezervacije.isEmpty()) {
					return ResponseEntity.status(404).body("Resources with foreign key: " +
							foreignKey + " do not exist");
				}else {
					return ResponseEntity.ok(rezervacije);
				}
				
			}
			return ResponseEntity.status(400).body("Invalid foreign key: " + foreignKey);
	}
	
	
	@DeleteMapping("/Rezervacija/id/{id}")
	public ResponseEntity<?> deleteRezervacija(@PathVariable int id){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been " +
					"successfully deleted");
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be deleted because it doesn't exist" );
	}
}
