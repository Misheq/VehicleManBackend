package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.PersonVehicleMapper;
import com.vehicleman.backend.entities.Vehicle;

//class PersonBaseIT {
//
//	@BeforeClass
//	public static void init() {
//		RestAssured.baseURI = "http://localhost";
//		RestAssured.port = 8080;
//		RestAssured.basePath = "/vehicleman/api/persons";
//	}
//}

public class PersonGetITests {// extends PersonBaseIT {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8082;
		RestAssured.basePath = "/vehicleman/api/persons";
	}

	@Test
	public void getListOfPersons() {
		given().when().get().then().statusCode(200);
	}

	//	@Test
	//	public void getPersonById() {
	//		given().when().get("/4").then().statusCode(200);
	//	}

	@Test
	public void getPersonByWrongId() {
		given().when().get("/-1").then().statusCode(404);
	}

	public Person dummyPerson() {
		Person p = new Person();
		p.setFirstName("fn");
		p.setLastName("ln");
		p.setEmail("a@a.aa");
		p.setCompanyName("c");
		p.setPhone("+36123456789");
		return p;
	}

	public Vehicle dummyVehicle() {
		Vehicle v = new Vehicle();
		v.setRegistrationNumber("BME-23456-AZ");
		v.setVehicleType("car");
		return v;
	}

	// POST
	@Test
	public void personPOSTTestWithoutVehicle() {

		PersonVehicleMapper pvm = new PersonVehicleMapper();
		pvm.setPerson(dummyPerson());

		String locationHeader = given().contentType("application/json").body(pvm).when().post().then().statusCode(201)
				.extract().header(HttpHeaders.LOCATION);

		Person result = given().when().get(locationHeader).then().statusCode(200).extract().as(Person.class);

		assertEquals(result.getFirstName(), "fn");
		assertEquals(result.getLastName(), "ln");
		assertEquals(result.getEmail(), "a@a.aa");
		assertEquals(result.getCompanyName(), "c");
		assertEquals(result.getPhone(), "+36123456789");
		assertEquals(result.getVehicles().isEmpty(), true);
	}

	@Test
	public void personPOSTTestWithNewVehicle() {

		Vehicle v = dummyVehicle();
		v.setRegistrationNumber(v.getRegistrationNumber() + "-NEW");
		Person p = dummyPerson();

		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(v);

		PersonVehicleMapper pvm = new PersonVehicleMapper();
		pvm.setPerson(p);
		pvm.setVehicles(vehicles);

		assertEquals(vehicles.size(), 1);
		assertEquals(pvm.getVehicles().size(), 1);

		String locationHeader = given().contentType("application/json").body(pvm).when().post().then().statusCode(201)
				.extract().header(HttpHeaders.LOCATION);

		Person result = given().when().get(locationHeader).then().statusCode(200).extract().as(Person.class);

		assertEquals(result, p);
		assertEquals(result.getVehicles().isEmpty(), false);
		assertEquals(result.getVehicles().size(), 1);

		Vehicle resultVehicle = result.getVehicles().get(0);
		assertEquals(resultVehicle.getRegistrationNumber(), v.getRegistrationNumber());
		assertEquals(resultVehicle.getVehicleType(), v.getVehicleType());
	}

	@Test
	public void personPOSTTestWithExistingVehicle() {

		Vehicle v = dummyVehicle();
		v.setVehicleId(5);

		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(v);

		Person p = dummyPerson();

		PersonVehicleMapper pvm = new PersonVehicleMapper();
		pvm.setPerson(p);
		pvm.setVehicles(vehicles);

		assertEquals(vehicles.size(), 1);
		assertEquals(pvm.getVehicles().size(), 1);

		String locationHeader = given().contentType("application/json").body(pvm).when().post().then().statusCode(201)
				.extract().header(HttpHeaders.LOCATION);

		Person result = given().when().get(locationHeader).then().statusCode(200).extract().as(Person.class);

		assertEquals(result, p);
		assertEquals(result.getVehicles().isEmpty(), false);
		assertEquals(result.getVehicles().get(0), v);
	}

	@Test
	public void personPOSTWrong() {

		PersonVehicleMapper pvm = new PersonVehicleMapper();

		given().contentType("application/json").body(pvm).when().post().then().statusCode(404);
	}

	// PUT
	public void updatePerson() {

	}

	// DELETE
	public void deletePerson() {

	}
}
