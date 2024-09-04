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
import rva.services.BioskopService;
import rva.services.RezervacijaService;
import rva.services.SalaService;

@RestController
public class SalaController {
	
	@Autowired
	private SalaService service;
	
	@Autowired
	private BioskopService bioskopService;
	
	@GetMapping("/Sala")
	public List<Sala> getAllSala(){
		return service.getAll();
	}
	
	@GetMapping("/Sala/id/{id}")
	public ResponseEntity<?> getSalaById(@PathVariable int id){
		Optional<Sala> sala = service.findById(id);
		if(sala.isPresent()) {
			return ResponseEntity.ok(sala.get());		
	}
	return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist");
	}
	
	
	@GetMapping("/Sala/naziv/{kapacitet}")
	public ResponseEntity<?> getSalaByKapacitet(@PathVariable int kapacitet){
		List<Sala> sala= service.getSalaByKapacitetGreaterThan(kapacitet);
		if(sala.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with kapacitet greater than:  "+kapacitet + " doesn not exist" ); 
		}
		return ResponseEntity.ok(sala);
	}
	
	
	@PostMapping("/Sala")
	public ResponseEntity<?> createSala(@RequestBody Sala sala){
		if(service.existsById(sala.getId())) {
			return ResponseEntity.status(409).body("Resurce already exists");
		}
		Sala savedSala = service.create(sala);
		URI uri = URI.create("/rezervacija/id/"+ savedSala.getId());
		return ResponseEntity.created(uri).body(savedSala);
	}
	
	
	@PutMapping("/Sala/id/{id}")
	public ResponseEntity<?> updateSala(@RequestBody Sala sala, @PathVariable int id)
	{
		Optional<Sala> updatedSala = service.update(sala, id);
		if(updatedSala.isPresent()) {
			return ResponseEntity.ok(updatedSala.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be updated because it doesn't exist" );
	}
	
	
	@GetMapping("/Sala/bioskop/{foreignKey}")
	public ResponseEntity<?> getSalaByBioskop(@PathVariable int foreignKey){
		Optional<Bioskop> bioskop = bioskopService.findById(foreignKey);
			if(bioskop.isPresent()) {
				List<Sala> sala = service.findByForeignKey(bioskop.get());
				if(sala.isEmpty()) {
					return ResponseEntity.status(404).body("Resources with foreign key: " +
							foreignKey + " do not exist");
				}else {
					return ResponseEntity.ok(sala);
				}
				
			}
			return ResponseEntity.status(400).body("Invalid foreign key: " + foreignKey);
	}
	
	@DeleteMapping("/Sala/id/{id}")
	public ResponseEntity<?> deleteSala(@PathVariable int id){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been " +
					"successfully deleted");
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be deleted because it doesn't exist" );
	}
}
