package co.swapi.planets.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class PlanetsTest {
	
	
	private static final Logger logger = LogManager.getLogger(PlanetsTest.class);
	
	@BeforeClass
	public static void setup() {

		logger.info("Setting up the PlanetsTest");

		RestAssured.baseURI = "https://swapi.co/api";
		RestAssured.basePath = "/planets";
		
		logger.info("Base URI is set to : " + RestAssured.baseURI);
		logger.info("BasePath is set to : " + RestAssured.basePath);

	}
	


	//This test verifies HTTP status code of the response.
	@Test
	public void verifyHttpStatus() {
		
		logger.info("Running verify HttpStatus test... ");	

		given().pathParam("palnetID", 1)
		.when().get("/{palnetID}")
		.then().assertThat().statusCode(200).log().ifValidationFails();
		
		logger.info("Finished verify HttpStatus test.");	

	}
	
	//This test verifies the Content type is JSON
	@Test
	public void verifyContentType() {
		logger.info("Running verify ContentType test... ");	
		
		given().pathParam("palnetID", 2)
		.when().get("/{palnetID}")
		.then().assertThat().contentType(ContentType.JSON).log().ifValidationFails();
		
		logger.info("Finished verify ContentType test.");	
	}
	
	
	//This test verifies response time of the rest call
	@Test
	public void verifyResponseTime() {
		logger.info("Running verify ResponseTime test... ");	
		
		given().pathParam("palnetID",5 )
		.when().get("/{palnetID}").
		then().time(lessThan(30000L)).log().ifValidationFails();
		
		logger.info("Finished verify ResponseTime test.");	
	}
	
	//This test verifies name of the planet 3
	@Test
	public void verifyPlanetName() {
		logger.info("Running verify PlanetName test... ");	
		
		given().pathParam("palnetID", 3)
		.when().get("/{palnetID}")
		.then().assertThat().body("name", equalTo("Yavin IV")).log().ifValidationFails();
		
		logger.info("Finished verify PlanetName test.");	
	}
	
	//This test verifies total number of planets
	@Test
	public void verifyPlanetsCount() {
		logger.info("Running verify PlanetsCount test... ");	
		
		given()
		.when().get()
		.then().assertThat().body("count", greaterThanOrEqualTo(61)).log().ifValidationFails();
		
		logger.info("Finished verify PlanetsCount test.");	
	}
	
	
	//This test verifies number of films on planet 2
	@Test
	public void verifyFilmsSize() {
		
		logger.info("Running verify verifyFilmsSize test... ");	
		
		Response response= 	given().pathParam("palnetID", 2)
		.when().get("/{palnetID}").then().contentType(ContentType.JSON).extract().response();
		
		List<String> films = response.jsonPath().getList("films");
		
		assertEquals(films.size(),2);	
		
		logger.info("Finished verify verifyFilmsSize test.");	
	}
	
	
	//Assuming residence don't reproduce or die on planet 1 and checking size of the residents
	@Test
	public void verifyResidentsSize() {
		logger.info("Running verify Residents size test... ");	
		
		Response response= 	given().pathParam("palnetID", 1)
		.when().get("/{palnetID}").then().contentType(ContentType.JSON).extract().response();
		
		List<String> residents = response.jsonPath().getList("residents");
		
		assertEquals(residents.size(),10);	
		
		logger.info("Finished verify Residents size test");	
	}
	
	@AfterClass
	public  static void cleanup() {
		
	 logger.info("Completed runing all the PlanetsTest");
		
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	
		
	}
}
