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
import rva.services.BioskopService;

@RestController
public class BioskopController {

	@Autowired
	private BioskopService service;
	
	@GetMapping("/Bioskop")
	//@RequestMapping(method = RequestMethod.GET, path="/bioskop")
	public List<Bioskop> getAllBioskop(){
		return service.getAll(); //vraca http response
	}
	
	@GetMapping("/Bioskop/id/{id}")
	public ResponseEntity<?> getBioskopById(@PathVariable int id){
		Optional<Bioskop> bioskop = service.findById(id);
		if(bioskop.isPresent()) {
			return ResponseEntity.ok(bioskop.get());	
	}
	return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist");
	}
	
	@GetMapping("/Bioskop/naziv/{naziv}")
	public ResponseEntity<?> getBioskopByNaziv(@PathVariable String naziv){
		List<Bioskop> bioskop = service.getBioskopByNaziv(naziv);
		if(bioskop.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with Naziv:  "+naziv + " doesn not exist" ); 
		}
		return ResponseEntity.ok(bioskop);
	}
	
	@PostMapping("/Bioskop")
	public ResponseEntity<?> createBioskop(@RequestBody Bioskop bioskop){
		if(service.existsById(bioskop.getId())) {
			return ResponseEntity.status(409).body("Resurce already exists");
		}
		Bioskop savedBioskop = service.create(bioskop);
		URI uri = URI.create("/Bioskop/id/"+ savedBioskop.getId());
		return ResponseEntity.created(uri).body(savedBioskop);
	}
	 
	@PutMapping("/Bioskop/id/{id}")
	public ResponseEntity<?> updateBioskop (@RequestBody Bioskop bioskop, @PathVariable int id)
	{
		Optional<Bioskop> updatedBioskop = service.update(bioskop, id);
		if(updatedBioskop.isPresent()) {
			return ResponseEntity.ok(updatedBioskop.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be updated because it doesn't exist" );
	}
	
	@DeleteMapping("/Bioskop/id/{id}")
	public ResponseEntity<?> deleteBioskop(@PathVariable int id){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been " +
					"successfully deleted");
		}
		return ResponseEntity.status(404).body("Resource with requested ID:  " + id + " could not be deleted because it doesn't exist" );
	}
	
}