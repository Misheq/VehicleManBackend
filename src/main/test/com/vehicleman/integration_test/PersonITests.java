package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;

public class PersonITests extends BaseITest {

	public PersonITests() {
		RestAssured.basePath = basePath + "/persons";
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
	//	@Test
	//	public void personPOSTTestWithoutVehicle() {
	//		Person person = dummyPerson();
	//
	////		PersonVehicleMapper pvm = new PersonVehicleMapper();
	////		pvm.setPerson(person);
	//
	//		String locationHeader = createResource(pvm);
	//		Person resultPerson = getResource(locationHeader, Person.class);
	//
	//		assertEquals(resultPerson, person);
	//		assertEquals(resultPerson.getVehicles().isEmpty(), true);
	//
	//		// update
	//		updatePerson(locationHeader);
	//	}

	//	@Test
	//	public void personPOSTTestWithNewVehicle() {
	//
	//		Vehicle v = dummyVehicle();
	//		v.setRegistrationNumber(v.getRegistrationNumber() + "-NEW");
	//		Person p = dummyPerson();
	//
	//		List<Vehicle> vehicles = new ArrayList<>();
	//		vehicles.add(v);
	//
	//		PersonVehicleMapper pvm = new PersonVehicleMapper();
	//		pvm.setPerson(p);
	//		pvm.setVehicles(vehicles);
	//
	//		assertEquals(vehicles.size(), 1);
	//		assertEquals(pvm.getVehicles().size(), 1);
	//
	//		String locationHeader = createResource(pvm);
	//		Person resultPerson = getResource(locationHeader, Person.class);
	//
	//		assertEquals(resultPerson, p);
	//		assertEquals(resultPerson.getVehicles().isEmpty(), false);
	//		assertEquals(resultPerson.getVehicles().size(), 1);
	//
	//		Vehicle resultVehicle = resultPerson.getVehicles().get(0);
	//		assertEquals(resultVehicle.getRegistrationNumber(), v.getRegistrationNumber());
	//		assertEquals(resultVehicle.getVehicleType(), v.getVehicleType());
	//
	//		// clean up data
	//		cleanUpData(locationHeader, resultPerson, resultVehicle);
	//	}

	private void cleanUpData(String locationHeader, Person resultPerson, Vehicle resultVehicle) {

		String personId = "/" + resultPerson.getPersonId();
		String vehicleId = "/" + resultVehicle.getVehicleId();
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

	//	@Test
	//	public void personPOSTTestWithExistingVehicle() {
	//
	//		Vehicle v = dummyVehicle();
	//		v.setVehicleId(5);
	//		v.setRegistrationNumber(v.getRegistrationNumber() + "-DEMO");
	//
	//		List<Vehicle> vehicles = new ArrayList<>();
	//		vehicles.add(v);
	//
	//		Person p = dummyPerson();
	//
	//		PersonVehicleMapper pvm = new PersonVehicleMapper();
	//		pvm.setPerson(p);
	//		pvm.setVehicles(vehicles);
	//
	//		assertEquals(vehicles.size(), 1);
	//		assertEquals(pvm.getVehicles().size(), 1);
	//
	//		String locationHeader = createResource(pvm);
	//		Person result = getResource(locationHeader, Person.class);
	//
	//		assertEquals(result, p);
	//		assertEquals(result.getVehicles().isEmpty(), false);
	//		assertEquals(result.getVehicles().get(0), v);
	//	}

	//	@Test
	//	public void personPOSTWrong() {
	//
	//		PersonVehicleMapper pvm = new PersonVehicleMapper();
	//
	//		given().contentType("application/json").body(pvm).when().post().then().statusCode(404);
	//	}

	// PUT
	public void updatePerson(String path) {
		Person person = dummyPerson();

		person.setCompanyName(person.getCompanyName() + " updated");
		person.setEmail(person.getEmail() + " updated");
		person.setFirstName(person.getFirstName() + " updated");
		person.setLastName(person.getLastName() + " updated");
		person.setPhone(person.getPhone() + " updated");

		String locationHeader = updateResource(path, person);
		Person resultManager = getResource(locationHeader, Person.class);

		assertEquals(resultManager.getEmail().contains("updated"), true);
		assertEquals(resultManager.getFirstName().contains("updated"), true);
		assertEquals(resultManager.getLastName().contains("updated"), true);
		assertEquals(resultManager.getCompanyName().contains("updated"), true);
		assertEquals(resultManager.getPhone().contains("updated"), true);
	}

}
