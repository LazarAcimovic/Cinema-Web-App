package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Bioskop;

@Repository
public interface BioskopRepository extends JpaRepository<Bioskop, Integer> {
	
	List<Bioskop> findByNazivContainingIgnoreCase(String naziv);
	

}
