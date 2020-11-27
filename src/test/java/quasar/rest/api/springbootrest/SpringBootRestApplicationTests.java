package quasar.rest.api.springbootrest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import quasar.rest.Exceptions.NotFoundException;
import quasar.rest.dto.PositionDTO;
import quasar.rest.models.Position;
import quasar.rest.models.Satelites;
import quasar.rest.services.SatellitesService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringBootRestApplicationTests {

	@Autowired
	private SatellitesService satellitesService;


	@Test
	void contextLoads() {
	}

	@Test
	public void testSetDataAndSearchSpaceshipStatusCode200(){

		List<Satelites> satelitesList = satellitesMock();

		ResponseEntity<String> httpResponse = satellitesService.setSatellitesDataAndSearchSpaceship(satelitesList, new ArrayList<>());

		Assert.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testGetSpaceshipDataStatusCode200(){

		ResponseEntity<String> httpResponse = satellitesService.getSpaceshipData(satellitesMock());

		Assert.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void testGetFinalMessageNotNull(){

		String response = satellitesService.getFinalMessage(satellitesMock());

		Assert.assertNotNull(response);
	}

	@Test
	public void testGetFinalPositionNotNull(){

		PositionDTO response = satellitesService.getFinalPosition(satellitesMock());

		Assert.assertNotNull(response.getPosition());
	}

	@Test
	public void testSaveSatelliteDataIsTrue(Satelites satelite, List<Satelites> list){

		List<Satelites> listTest = new ArrayList<>();

		Satelites satelites = new Satelites();

		Boolean response = satellitesService.saveSatelliteData(satelites, listTest);

		Assert.assertEquals(true, response);
	}


	private List<Satelites> satellitesMock(){

		List<Satelites> satelitesList = new ArrayList<>();
		Satelites sateliteKe = new Satelites();
		sateliteKe.setName("kenobi");
		sateliteKe.setDistance(500);
		sateliteKe.setMessage(new String[]{"", "es", "un", "mensaje", "secreto"});
		satelitesList.add(sateliteKe);

		Satelites sateliteSk = new Satelites();
		sateliteSk.setName("skywalker");
		sateliteSk.setDistance(202);
		sateliteSk.setMessage(new String[]{"", "es", "", "",  "secreto"});
		satelitesList.add(sateliteSk);

		Satelites sateliteSa = new Satelites();
		sateliteSa.setName("sato");
		sateliteSa.setDistance(560);
		sateliteSa.setMessage(new String[]{"este", "", "un",  "", ""});
		satelitesList.add(sateliteSa);

		return satelitesList;
	}

}
