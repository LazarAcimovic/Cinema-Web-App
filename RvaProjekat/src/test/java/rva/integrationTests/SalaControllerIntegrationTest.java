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

import rva.models.Rezervacija;
import rva.models.Sala;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SalaControllerIntegrationTest {

	@Autowired
	TestRestTemplate template; 
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Sala>> response =template.exchange("/Sala",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Sala>>() {});  
		
		ArrayList<Sala> list = (ArrayList<Sala>) response.getBody();
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
		void testGetAllSalas() {
			ResponseEntity<List<Sala>> response =template.exchange("/Sala",HttpMethod.GET,null,
					new ParameterizedTypeReference<List<Sala>>() {});  
		
			int statusCode = response.getStatusCode().value();
			
			List <Sala> sale = response.getBody();
			
			assertEquals(200,statusCode);
			
			assertTrue(!sale.isEmpty());
			
		
		}
		
		@Test
		@Order(2)
		void testGetSalaById() {
			
			int id = 1;
			ResponseEntity<Sala> response =template.exchange("/Sala/id/" + id,HttpMethod.GET,null,Sala.class);  
		
			int statusCode = response.getStatusCode().value();
			
			
			
			assertEquals(200,statusCode);
			assertEquals(id,response.getBody().getId());
		
			
		
		}
		
		
		@Test
		@Order(3)
		void testGetSalasByKapacitetGreaterThan() {
			
			int kapacitet = 110;
			
			ResponseEntity<List<Sala>> response=template.exchange("/Sala//naziv/" + kapacitet ,HttpMethod.GET,null,
					new ParameterizedTypeReference<List<Sala>>() {});  
		
			int statusCode = response.getStatusCode().value();
			
			List <Sala> sale = response.getBody();
			
			assertEquals(200,statusCode);
			assertNotNull(sale.get(0));
			
			for (Sala a: sale) {
				assertTrue(a.getKapacitet() > kapacitet);
			
			}
			
			
		
			
		
		}
		
		@Test
		@Order(4)
		void testCreateSala() {
			
			Sala sala = new Sala();
			sala.setKapacitet(110);
			sala.setBroj_redova(7);
			
			
			HttpEntity<Sala>  entity = new HttpEntity<Sala>(sala);
			createHighestId();
			ResponseEntity<Sala> response = template.exchange("/Sala", HttpMethod.POST,entity,Sala.class);
			int statusCode = response.getStatusCode().value();
			
			
			assertEquals(201,statusCode);
			
			
			
			}
		
		
		@Test
		@Order(5)
		void testUpdateSala() {

			Sala sala = new Sala();
			sala.setKapacitet(130);
			sala.setBroj_redova(10);
			
			HttpEntity<Sala>  entity = new HttpEntity<Sala>(sala);
			getHighestId();
			ResponseEntity<Sala> response = template.exchange("/Sala/id/" + highestId, HttpMethod.PUT,entity,Sala.class);
			int statusCode = response.getStatusCode().value();
			
			assertEquals(200,statusCode);
			
			assertTrue(response.getBody() instanceof Sala);

			assertEquals(sala.getKapacitet(),response.getBody().getKapacitet());
			assertEquals(sala.getBroj_redova(),response.getBody().getBroj_redova());
			
		}
		
		@Test
		@Order(6)
		void testDeleteSala() {
			
			getHighestId();
			ResponseEntity<String> response = template.exchange("/Sala/id/" + highestId,HttpMethod.DELETE, null,String.class);
			
			int statusCode = response.getStatusCode().value();
			
			assertEquals(200,statusCode);
			
			
			
		}
		
		@Test
		@Order(7) 
		void testGetSalaByBioskop(){
			
				
				int bioskop = 1;
				
				ResponseEntity<List<Sala>> response=template.exchange("/Sala/bioskop/" + bioskop ,HttpMethod.GET,null,
						new ParameterizedTypeReference<List<Sala>>() {}); 
			
				int statusCode = response.getStatusCode().value();
				
				List <Sala> sale = response.getBody();
				
				assertEquals(200,statusCode);
				assertNotNull(sale.get(0));
				
				assertEquals(bioskop,response.getBody().get(0).getBioskop().getId());
				
			
			
				
			
			
		}
}
