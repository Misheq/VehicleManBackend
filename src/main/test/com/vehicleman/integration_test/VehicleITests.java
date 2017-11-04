package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Vehicle;

public class VehicleITests extends BaseITest {

	public VehicleITests() {
		RestAssured.basePath = basePath + "/vehicles";
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

	//	// POST
	//	@Test
	//	public void postVehicle() {
	//
	//		Vehicle vehicle = dummyVehicle();
	//
	//		List<Vehicle> vehicles = new ArrayList<>();
	//		vehicles.add(vehicle);
	//
	//		PersonVehicleMapper pvm = new PersonVehicleMapper();
	//		pvm.setVehicles(vehicles);
	//
	//		// create vehicle
	//		String locationHeader = createResource(pvm);
	//
	//		// check if it has been created
	//		Vehicle resultVehicle = getResource(locationHeader, Vehicle.class);
	//
	//		assertEquals(resultVehicle.getRegistrationNumber(), vehicle.getRegistrationNumber());
	//		assertEquals(resultVehicle.getVehicleType(), vehicle.getVehicleType());
	//		assertEquals(resultVehicle.getPerson(), null);
	//
	//		// post again and check for conflict
	//		checkResourceConflict(pvm);
	//
	//		// get vehicle id
	//		String vehicleId = "/" + resultVehicle.getVehicleId();
	//
	//		// put
	//		updateVehicle(vehicleId); // or use locationHeader instead
	//
	//		// delete the vehicle for consistency
	//		deleteResource(vehicleId); // or use locationHeader instead
	//
	//		// verify that the vehicle with the ID is gone
	//		checkResourceNotFound(locationHeader);
	//	}

	public void updateVehicle(String vehicleId) {
		Vehicle vehicle = dummyVehicle();

		vehicle.setRegistrationNumber(vehicle.getRegistrationNumber() + " updated");
		vehicle.setVehicleType(vehicle.getVehicleType() + " updated");

		String locationHeader = updateResource(vehicleId, vehicle);
		Vehicle resultVehicle = getResource(locationHeader, Vehicle.class);

		assertEquals(resultVehicle.getRegistrationNumber().contains("updated"), true);
		assertEquals(resultVehicle.getVehicleType().contains("updated"), true);
	}

	//	@Test
	//	public void postVehicleWrong() {
	//
	//		PersonVehicleMapper pvm = new PersonVehicleMapper();
	//
	//		given().contentType("application/json").body(pvm).when().post().then().statusCode(404);
	//	}
}
