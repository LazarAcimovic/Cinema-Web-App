package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Bioskop;
import rva.models.Sala;

@Service
public interface SalaService extends CrudService<Sala> {

	List<Sala> getSalaByKapacitetGreaterThan(int kapacitet);
	
	List<Sala> findByForeignKey(Bioskop bioskop);
}
