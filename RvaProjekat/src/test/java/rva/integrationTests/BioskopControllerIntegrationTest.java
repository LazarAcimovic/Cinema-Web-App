package rva.integrationTests;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BioskopControllerIntegrationTest {
	
	@Autowired
	TestRestTemplate template; 
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Bioskop>> response =template.exchange("/Bioskop",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Bioskop>>() {}); 
		
		ArrayList<Bioskop> list = (ArrayList<Bioskop>) response.getBody();
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
	void testGetAllBioskops() {
		ResponseEntity<List<Bioskop>> response =template.exchange("/Bioskop",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Bioskop>>() {}); 
	
		int statusCode = response.getStatusCode().value();
		
		List <Bioskop> bioskopi = response.getBody();
		
		assertEquals(200,statusCode);
		
		assertTrue(!bioskopi.isEmpty());
		
	
	}
	
	@Test
	@Order(2)
	void testGetBioskopById() {
		
		int id = 1;
		ResponseEntity<Bioskop> response =template.exchange("/Bioskop/id/" + id,HttpMethod.GET,null,Bioskop.class);  
	
		int statusCode = response.getStatusCode().value();
		
		
		
		assertEquals(200,statusCode);
		assertEquals(id,response.getBody().getId());
	
		
	
	}
	
	@Test
	@Order(3)
	void testGetBioskopsByNaziv() {
		
		String naziv = "Galaksija";
		
		ResponseEntity<List<Bioskop>> response=template.exchange("/Bioskop/naziv/" + naziv ,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Bioskop>>() {});  
	
		int statusCode = response.getStatusCode().value();
		
		List <Bioskop> bioskopi = response.getBody();
		
		assertEquals(200,statusCode);
		assertNotNull(bioskopi.get(0));
		
		for (Bioskop a: bioskopi) {
			assertTrue(a.getNaziv().contains(naziv));
		
		}
		
		
	
		
	
	}
	
	@Test
	@Order(4)
	void testCreateBioskop() {
		
		Bioskop bioskop = new Bioskop();
		bioskop.setNaziv("Novi naziv");
		bioskop.setAdresa("Nova adresa");
		
		
		HttpEntity<Bioskop>  entity = new HttpEntity<Bioskop>(bioskop); 
		createHighestId();
		ResponseEntity<Bioskop> response = template.exchange("/Bioskop", HttpMethod.POST,entity,Bioskop.class);
		int statusCode = response.getStatusCode().value();
		
		
		assertEquals(201,statusCode);
		
		assertEquals("/Bioskop/id/" + highestId,response.getHeaders().getLocation().getPath());
		
		assertEquals(bioskop.getNaziv(),response.getBody().getNaziv());
		assertEquals(bioskop.getAdresa(),response.getBody().getAdresa());
		
		}
	
	@Test
	@Order(5)
	void testUpdateBioskop() {

		Bioskop bioskop = new Bioskop();
		bioskop.setNaziv("PUT naziv");
		bioskop.setAdresa("PUT adresa");
		
		
		HttpEntity<Bioskop>  entity = new HttpEntity<Bioskop>(bioskop);
		getHighestId();
		ResponseEntity<Bioskop> response = template.exchange("/Bioskop/id/" + highestId, HttpMethod.PUT,entity,Bioskop.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		
		assertTrue(response.getBody() instanceof Bioskop);

		assertEquals(bioskop.getNaziv(),response.getBody().getNaziv());
		assertEquals(bioskop.getAdresa(),response.getBody().getAdresa());
		
	}
	
	@Test
	@Order(6)
	void testDeleteBioskop() {
		
		getHighestId();
		ResponseEntity<String> response = template.exchange("/Bioskop/id/" + highestId,HttpMethod.DELETE, null,String.class);
		
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		
		
		
	}
}
