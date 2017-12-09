package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;

import javax.ws.rs.core.HttpHeaders;

import org.junit.BeforeClass;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Manager;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.utils.ApiConstants;

public class BaseITest {

	public static String basePath;
	public static final String REGISTER_PATH = ApiConstants.BASE_URL + "auth/register";
	public static final String USERNAME = "mihael@sap.com";
	public static final String PASSWORD = "asdasd";

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = Integer.parseInt(ApiConstants.PORT);
		RestAssured.authentication = RestAssured.preemptive().basic(USERNAME, PASSWORD);
		basePath = "/vehicleman/api";
	}

	public Person dummyPerson() {
		Person p = new Person();
		p.setFirstName("fn");
		p.setLastName("ln");
		p.setEmail("test_person018@testmail.com");
		p.setCompanyName("c");
		p.setPhone("+36123456789");
		return p;
	}

	// should always contain an non existing vehicle (different reg.no.)
	public Vehicle dummyVehicle() {
		Vehicle v = new Vehicle();
		v.setRegistrationNumber("BME-555325-SG");
		v.setVehicleType("car");
		return v;
	}

	public Manager dummyManager() {
		Manager m = new Manager();
		m.setFirstName("MFN");
		m.setLastName("MLN");
		m.setEmail("test_manager_001@sap.com");
		m.setPhone("+36 1 123 123");
		m.setCompanyName("HelloCity Ltd.");
		m.setPassword("asdasd");

		return m;
	}

	// creates a new resources and returns the location header
	public String createResource(Object bodyPayload) {
		// "" means default RestAssured basePath
		return createResource(bodyPayload, "");
	}

	public String createResource(Object bodyPayload, String registerEndpoint) {
		return given().contentType("application/json").body(bodyPayload).when().post(registerEndpoint).then()
				.statusCode(201).extract().header(HttpHeaders.LOCATION);
	}

	// updates the resource and returns the location header
	public String updateResource(String resourceId, Object bodyPayload) {
		System.out.println("resource Id " + resourceId);
		return given().contentType("application/json").body(bodyPayload).when().put(resourceId).then().statusCode(200)
				.extract().header(HttpHeaders.LOCATION);
	}

	// checks if the resource exists and returns the object
	public <T> T getResource(String locationHeader, Class<T> responseClass) {
		return given().when().get(locationHeader).then().statusCode(200).extract().as(responseClass);
	}

	public void deleteResource(String path) {
		given().when().delete(path).then().statusCode(204);
	}

	public void checkResourceNotFound(String path) {
		given().when().get(path).then().statusCode(404);
	}

	public void checkResourceConflict(Object bodyPayload) {
		given().contentType("application/json").body(bodyPayload).when().post().then().statusCode(409);
	}
}
