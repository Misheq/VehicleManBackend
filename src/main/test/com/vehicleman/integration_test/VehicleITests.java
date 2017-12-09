package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Vehicle;

public class VehicleITests extends BaseITest {

	private static final String ENDPOINT = "/vehicles";
	private static String vehicleId;

	public VehicleITests() {
		RestAssured.basePath = basePath + ENDPOINT;
	}

	// GET
	@Test
	public void getListOfVehicles() {
		given().when().get().then().statusCode(200);
	}

	@Test
	public void getVehicleById() {
		given().when().get("/176").then().statusCode(200);
	}

	@Test
	public void getVehicleByWrongId() {
		given().when().get("/-1").then().statusCode(404);
	}

	// POST
	@Test
	public void postVehicle() {
		// create vehicle
		createVehicle();
		
		// post again and check for conflict
		createConflictVehicleConflict();

		// put
		updateVehicle(); // or use locationHeader instead

		// delete the vehicle for consistency
		deleteVehicle(); // or use locationHeader instead

		// verify that the vehicle with the ID is gone
		checkVehicleNotFound();
	}
	
	private void createVehicle() {
		Vehicle vehicle = dummyVehicle();

		// create vehicle
		String locationHeader = createResource(vehicle);

		// check if it has been created
		Vehicle resultVehicle = getResource(locationHeader, Vehicle.class);
		
		// get vehicle id
		vehicleId = "/" + resultVehicle.getVehicleId();

		assertEquals(resultVehicle.getRegistrationNumber(), vehicle.getRegistrationNumber());
		assertEquals(resultVehicle.getVehicleType(), vehicle.getVehicleType());
		assertEquals(resultVehicle.getPerson(), null);
	}

	private void createConflictVehicleConflict() {
		checkResourceConflict(dummyVehicle());
	}
	
	private void updateVehicle() {
		Vehicle vehicle = dummyVehicle();

		vehicle.setRegistrationNumber(vehicle.getRegistrationNumber() + " updated");
		vehicle.setVehicleType(vehicle.getVehicleType() + " updated");

		String locationHeader = updateResource(vehicleId, vehicle);
		Vehicle resultVehicle = getResource(locationHeader, Vehicle.class);

		assertEquals(resultVehicle.getRegistrationNumber().contains("updated"), true);
		assertEquals(resultVehicle.getVehicleType().contains("updated"), true);
	}

	private void deleteVehicle() {
		deleteResource(vehicleId);
	}
	
	private void checkVehicleNotFound() {
		checkResourceNotFound(vehicleId);
	}
}
