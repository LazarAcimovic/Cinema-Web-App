package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import rva.models.Bioskop;
import rva.models.Film;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmControllerIntegrationTest {

	@Autowired
	TestRestTemplate template; 
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Film>> response =template.exchange("/Film",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Film>>() {}); 
		
		ArrayList<Film> list = (ArrayList<Film>) response.getBody();
		for (int i = 0; i < list.size(); i++) {
			if (highestId <= list.get(i).getId()) {
				highestId = list.get(i).getId() + 1;
			}
		}
	}
	
	void getHighestId() {
		createHighestId();
		highestId--;
	}
	
	
		@Test
		@Order(1)
		void testGetAllFilms() {
			ResponseEntity<List<Film>> response =template.exchange("/Film",HttpMethod.GET,null,
					new ParameterizedTypeReference<List<Film>>() {}); 
		
			int statusCode = response.getStatusCode().value();
			
			List <Film> filmovi = response.getBody();
			
			assertEquals(200,statusCode);
			
			assertTrue(!filmovi.isEmpty());
			
		
		}
		
		@Test
		@Order(2)
		void testGetFilmById() {
			
			int id = 1;
			ResponseEntity<Film> response =template.exchange("/Film/id/" + id,HttpMethod.GET,null,Film.class); //endpoint, metoda,telo, povratni tip 
		
			int statusCode = response.getStatusCode().value();
			
			
			
			assertEquals(200,statusCode);
			assertEquals(id,response.getBody().getId());
		
			
		
		}
		
		@Test
		@Order(3)
		void testGetFilmsByNaziv() {
			
			String naziv = "Inception";
			
			ResponseEntity<List<Film>> response=template.exchange("/Film/naziv/" + naziv ,HttpMethod.GET,null,
					new ParameterizedTypeReference<List<Film>>() {}); 
		
			int statusCode = response.getStatusCode().value();
			
			List <Film> filmovi = response.getBody();
			
			assertEquals(200,statusCode);
			assertNotNull(filmovi.get(0));
			
			for (Film a: filmovi) {
				assertTrue(a.getNaziv().contains(naziv));
			}
			
			
		
			
		
		}
		
		@Test
		@Order(4)
		void testCreateFilm() {
			
			Film film = new Film();
			film.setNaziv("Novi naziv");
			film.setZanr("Novi zanr");
			
			
			HttpEntity<Film>  entity = new HttpEntity<Film>(film);
			createHighestId();
			ResponseEntity<Film> response = template.exchange("/Film", HttpMethod.POST,entity,Film.class);
			int statusCode = response.getStatusCode().value();
			
			
			assertEquals(201,statusCode);
			
			assertEquals("/Film/id/" + highestId,response.getHeaders().getLocation().getPath());
			
			assertEquals(film.getNaziv(),response.getBody().getNaziv());
			assertEquals(film.getZanr(),response.getBody().getZanr());
			
			}
		
		
		@Test
		@Order(5)
		void testUpdateFilm() {

			Film film = new Film();
			film.setNaziv("PUT naziv");
			film.setZanr("PUT zanr");
			
			HttpEntity<Film>  entity = new HttpEntity<Film>(film);
			getHighestId();
			ResponseEntity<Film> response = template.exchange("/Film/id/" + highestId, HttpMethod.PUT,entity,Film.class);
			int statusCode = response.getStatusCode().value();
			
			assertEquals(200,statusCode);
			
			assertTrue(response.getBody() instanceof Film);

			assertEquals(film.getNaziv(),response.getBody().getNaziv());
			assertEquals(film.getZanr(),response.getBody().getZanr());
			
		}
		
		@Test
		@Order(6)
		void testDeletefilm() {
			
			getHighestId();
			ResponseEntity<String> response = template.exchange("/Film/id/" + highestId,HttpMethod.DELETE, null,String.class);
			
			int statusCode = response.getStatusCode().value();
			
			assertEquals(200,statusCode);
			
			
			
		}
}
