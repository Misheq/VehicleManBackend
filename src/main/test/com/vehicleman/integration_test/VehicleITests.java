package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.PersonVehicleMapper;
import com.vehicleman.backend.entities.Vehicle;

public class VehicleITests {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8082;
		RestAssured.basePath = "/vehicleman/api/vehicles";
	}

	// GET
	@Test
	public void getListOfVehicles() {
		given().when().get().then().statusCode(200);
	}

	@Test
	public void getVehicleById() {
		given().when().get("/5").then().statusCode(200);
	}

	@Test
	public void getVehicleByWrongId() {
		given().when().get("/-1").then().statusCode(404);
	}

	// POST
	@Test
	public void postVehicle() {

		Vehicle v = new Vehicle();
		v.setRegistrationNumber("belaaaa");
		v.setVehicleType("car");

		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(v);

		PersonVehicleMapper pvm = new PersonVehicleMapper();
		pvm.setVehicles(vehicles);

		String locationHeader = given().contentType("application/json").body(pvm).when().post().then().statusCode(201)
				.extract().header(HttpHeaders.LOCATION);

		// check if it has been created
		Vehicle result = given().when().get(locationHeader).then().statusCode(200).extract().as(Vehicle.class);

		assertEquals(result.getRegistrationNumber(), v.getRegistrationNumber());
		assertEquals(result.getVehicleType(), v.getVehicleType());
		assertEquals(result.getPerson(), null);

		// post again and check for conflict
		given().contentType("application/json").body(pvm).when().post().then().statusCode(409);

		String vehicleId = String.valueOf(result.getVehicleId());

		// delete the vehicle for consistency
		given().when().delete(vehicleId).then().statusCode(204);

		// verify that the vehicle with the ID is gone
		given().when().get(locationHeader).then().statusCode(404);
	}

	@Test
	public void postVehicleWrong() {

		PersonVehicleMapper pvm = new PersonVehicleMapper();

		given().contentType("application/json").body(pvm).when().post().then().statusCode(404);
	}

}
