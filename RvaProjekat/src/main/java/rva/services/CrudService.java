package rva.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
	
	List<T> getAll();
	
	boolean existsById(int id);
	
	T create(T t); //npr ako imamo artikl, metoda ce vracati artikl
	
	Optional<T> update(T t, int id);
	
	Optional<T> findById(int id);
	
	void delete(int id);
	
	
}
