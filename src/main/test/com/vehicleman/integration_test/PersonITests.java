package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.utils.ApiConstants;

public class PersonITests extends BaseITest {

	private static final String ENDPOINT = "/persons";
	private static String personId;
	private static String vehicleId;

	public PersonITests() {
		RestAssured.basePath = basePath + ENDPOINT;
	}

	// GET
	@Test
	public void getListOfPersons() {
		given().when().get().then().statusCode(200);
	}

	@Test
	public void getPersonById() {
		given().when().get("/172").then().statusCode(200);
	}

	@Test
	public void getPersonByWrongId() {
		given().when().get("/-1").then().statusCode(404);
	}

	// POST
	@Test
	public void personPOSTTestWithoutVehicle() {
		createPerson();
		createConflictPerson();
		updatePerson();
		deletePerson();
	}

	private void createPerson() {
		Person person = dummyPerson();

		String locationHeader = createResource(person);
		Person resultPerson = getResource(locationHeader, Person.class);

		personId = "/" + resultPerson.getPersonId();

		assertEquals(resultPerson, person);
		assertEquals(resultPerson.getVehicles().isEmpty(), true);
	}

	private void createConflictPerson() {
		given().contentType("application/json").body(dummyPerson()).when().post().then().statusCode(409);
	}

	// PUT
	private void updatePerson() {
		Person person = dummyPerson();

		person.setCompanyName(person.getCompanyName() + " updated");
		person.setEmail(person.getEmail() + " updated");
		person.setFirstName(person.getFirstName() + " updated");
		person.setLastName(person.getLastName() + " updated");
		person.setPhone(person.getPhone() + " updated");

		String locationHeader = updateResource(personId, person);
		Person resultManager = getResource(locationHeader, Person.class);

		assertEquals(resultManager.getEmail().contains("updated"), true);
		assertEquals(resultManager.getFirstName().contains("updated"), true);
		assertEquals(resultManager.getLastName().contains("updated"), true);
		assertEquals(resultManager.getCompanyName().contains("updated"), true);
		assertEquals(resultManager.getPhone().contains("updated"), true);
	}

	private void deletePerson() {
		deleteResource(personId);
		checkResourceNotFound(personId);
	}

	@Test
	public void personPOSTTestWithNewVehicle() {

		Person person = dummyPerson();

		Vehicle v = dummyVehicle();
		v.setRegistrationNumber(v.getRegistrationNumber() + "-NEW");
		person.setVehicles(new ArrayList<>(Arrays.asList(v)));

		String locationHeader = createResource(person);
		System.out.println(locationHeader);
		Person resultPerson = getResource(locationHeader, Person.class);

		personId = "/" + resultPerson.getPersonId();

		assertEquals(resultPerson, person);
		assertEquals(resultPerson.getVehicles().isEmpty(), false);
		assertEquals(resultPerson.getVehicles().size(), 1);

		Vehicle resultVehicle = resultPerson.getVehicles().get(0);
		vehicleId = "/" + resultVehicle.getVehicleId();
		
		assertEquals(resultVehicle.getRegistrationNumber(), v.getRegistrationNumber());
		assertEquals(resultVehicle.getVehicleType(), v.getVehicleType());

		// clean up data
		cleanUpData(locationHeader);
	}

	private void cleanUpData(String locationHeader) {

		String vehicleURL = locationHeader.replace("persons" + personId, "vehicles" + vehicleId);

		// delete person for consistency
		deleteResource(personId);

		// check if the person was deleted
		checkResourceNotFound(personId);

		// delete vehicle
		deleteResource(vehicleURL);

		// check if the vehicle was deleted
		checkResourceNotFound(vehicleURL);
	}

}
