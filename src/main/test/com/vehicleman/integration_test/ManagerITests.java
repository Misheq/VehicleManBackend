package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.HttpHeaders;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Manager;

public class ManagerITests {

	public static final String ENDPOINT = "/managers";
	public static String M_ID;

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8082;
		RestAssured.basePath = "/vehicleman/api" + ENDPOINT;
	}

	public Manager dummyManager() {
		Manager m = new Manager();
		m.setFirstName("MFN");
		m.setLastName("MLN");
		m.setEmail("new1@test.com");
		m.setPhone("+36 1 123 123");
		m.setCompanyName("HelloCity Ltd.");

		return m;
	}

	public Manager verifyManager(String url) {
		return given().when().get(url).then().statusCode(200).extract().as(Manager.class);
	}

	// GET

	@Test
	public void listOfManagers() {
		given().when().get().then().statusCode(200);
	}

	@Test
	public void getManagerById() {
		given().when().get("/1").then().statusCode(200);
	}

	@Test
	public void getManagerByWrongId() {
		given().when().get("/-1").then().statusCode(404);
	}

	// POST
	@Test
	public void testManagerServicePOST() {
		createManager();
		createManagerConflict();
		updateManager();
		deleteManager();
	}

	public void createManager() {

		Manager manager = dummyManager();

		String locationHeader = given().contentType("application/json").body(manager).when().post().then()
				.statusCode(201).extract().header(HttpHeaders.LOCATION);

		Manager m = verifyManager(locationHeader);
		M_ID = "/" + m.getManagerId();
		assertEquals(m.getEmail(), manager.getEmail());
	}

	public void createManagerConflict() {
		given().contentType("application/json").body(dummyManager()).when().post().then().statusCode(409);
	}

	//	@Test
	public void updateManager() {
		Manager manager = dummyManager();

		manager.setCompanyName(manager.getCompanyName() + " updated");
		manager.setEmail(manager.getEmail() + " updated");
		manager.setFirstName(manager.getFirstName() + " updated");
		manager.setLastName(manager.getLastName() + " updated");
		manager.setPhone(manager.getPhone() + " updated");

		//		String sId = "/170";
		String locationHeader = given().contentType("application/json").body(manager).when().put(M_ID).then()
				.statusCode(200).extract().header(HttpHeaders.LOCATION);

		Manager m = given().when().get(locationHeader).then().statusCode(200).extract().as(Manager.class);

		assertEquals(m.getEmail().contains("updated"), true);
		assertEquals(m.getFirstName().contains("updated"), true);
		assertEquals(m.getLastName().contains("updated"), true);
		assertEquals(m.getCompanyName().contains("updated"), true);
		assertEquals(m.getPhone().contains("updated"), true);
	}

	public void deleteManager() {
		given().when().delete(M_ID).then().statusCode(204);

		given().when().get(M_ID).then().statusCode(404);
	}

}
