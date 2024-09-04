package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Bioskop;
import rva.models.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {

	List<Sala> findByKapacitetGreaterThanOrderByKapacitetDesc(int kapacitet);
	
	List<Sala> findByBioskop(Bioskop bioskop);
}
