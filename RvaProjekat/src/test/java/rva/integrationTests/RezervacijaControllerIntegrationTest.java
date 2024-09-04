package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Date;
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

import rva.models.Film;
import rva.models.Rezervacija;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RezervacijaControllerIntegrationTest {

	@Autowired
	TestRestTemplate template; 
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Rezervacija>> response =template.exchange("/Rezervacija",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Rezervacija>>() {}); 
		
		ArrayList<Rezervacija> list = (ArrayList<Rezervacija>) response.getBody();
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
		void testGetAllRezervacija() {
			ResponseEntity<List<Rezervacija>> response =template.exchange("/Rezervacija",HttpMethod.GET,null,
					new ParameterizedTypeReference<List<Rezervacija>>() {}); 
		
			int statusCode = response.getStatusCode().value();
			
			List <Rezervacija> rezervacije = response.getBody();
			
			assertEquals(200,statusCode);
			
			assertTrue(!rezervacije.isEmpty());
			
		
		}
		
		@Test
		@Order(2)
		void testGetRezervacijaById() {
			
			int id = 1;
			ResponseEntity<Rezervacija> response =template.exchange("/Rezervacija/id/" + id,HttpMethod.GET,null,Rezervacija.class);  
		
			int statusCode = response.getStatusCode().value();
			
			
			
			assertEquals(200,statusCode);
			assertEquals(id,response.getBody().getId());
		
			
			
		
		}
		
		
		@Test
		@Order(3)
		void testGetRezervacijasByPlaceno() {
			
			boolean placeno = true;
			
			ResponseEntity<List<Rezervacija>> response=template.exchange("/Rezervacija/naziv/" + placeno ,HttpMethod.GET,null,
					new ParameterizedTypeReference<List<Rezervacija>>() {}); 
		
			int statusCode = response.getStatusCode().value();
			
			List <Rezervacija> rezervacije = response.getBody();
			
			assertEquals(200,statusCode);
			assertNotNull(rezervacije.get(0));
			
			
			for(Rezervacija r: rezervacije) {
				assertTrue(r.isPlaceno());
				
			}
			
			
		
		}
		
		@Test
		@Order(4)
		void testCreateRezervacija() {
			
			Rezervacija rezervacija = new Rezervacija();
			rezervacija.setBroj_osoba(30);
			rezervacija.setCena_karte(500.00);
			rezervacija.setPlaceno(true);
			rezervacija.setDatum(new Date());
			
			
			HttpEntity<Rezervacija>  entity = new HttpEntity<Rezervacija>(rezervacija);
			createHighestId();
			ResponseEntity<Rezervacija> response = template
					.exchange("/Rezervacija", HttpMethod.POST,entity,Rezervacija.class);
			int statusCode = response.getStatusCode().value();
			
			
			assertEquals(201,statusCode);
			
			assertEquals("/Rezervacija/id/" + highestId,response.getHeaders().getLocation().getPath());
			
			assertEquals(rezervacija.getBroj_osoba(),response.getBody().getBroj_osoba());
			assertEquals(rezervacija.getCena_karte(),response.getBody().getCena_karte());
			
			}
		
		
		@Test
		@Order(5)
		void testUpdateRezervacija() {

			Rezervacija rezervacija = new Rezervacija();
			rezervacija.setBroj_osoba(60);
			rezervacija.setCena_karte(300);
			
			HttpEntity<Rezervacija>  entity = new HttpEntity<Rezervacija>(rezervacija);
			getHighestId();
			ResponseEntity<Rezervacija> response = template.exchange("/Rezervacija/id/" + highestId, HttpMethod.PUT,entity,Rezervacija.class);
			int statusCode = response.getStatusCode().value();
			
			assertEquals(200,statusCode);
			
			assertTrue(response.getBody() instanceof Rezervacija);

			assertEquals(rezervacija.getBroj_osoba(),response.getBody().getBroj_osoba());
			assertEquals(rezervacija.getCena_karte(),response.getBody().getCena_karte());
			
		}
		
		@Test
		@Order(6)
		void testDeleteRezervacija() {
			
			getHighestId();
			ResponseEntity<String> response = template.exchange("/Rezervacija/id/" + highestId,HttpMethod.DELETE, null,String.class);
			
			int statusCode = response.getStatusCode().value();
			
			assertEquals(200,statusCode);
			
			
			
		}
		
		@Test
		@Order(7) 
		void testGetRezervacijaBySala(){
			
				
				int sala = 1;
				
				ResponseEntity<List<Rezervacija>> response=template.exchange("/Rezervacija/sala/" + sala ,HttpMethod.GET,null,
						new ParameterizedTypeReference<List<Rezervacija>>() {});  
			
				int statusCode = response.getStatusCode().value();
				
				List <Rezervacija> rezervacije = response.getBody();
				
				assertEquals(200,statusCode);
				assertEquals(sala,response.getBody().get(0).getSala().getId());
				
				assertEquals(sala,rezervacije.get(0).getSala().getId());
				
				
				
				
				
			
			
				
			
			
		}
		
		@Test
		@Order(8) 
		void testGetRezervacijaByFilm(){
			
				
				int film = 2;
				
				ResponseEntity<List<Rezervacija>> response=template.exchange("/Rezervacija/film/" + film ,HttpMethod.GET,null,
						new ParameterizedTypeReference<List<Rezervacija>>() {});  
			
				int statusCode = response.getStatusCode().value();
				
				List <Rezervacija> rezervacije = response.getBody();
				
				assertEquals(200,statusCode);
				assertEquals(film,response.getBody().get(0).getFilm().getId());
				
			
			
				
			
			
		}
}
