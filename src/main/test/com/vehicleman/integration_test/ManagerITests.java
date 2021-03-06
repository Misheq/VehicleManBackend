package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Manager;

public class ManagerITests extends BaseITest {

	private static final String ENDPOINT = "/managers";
	private static String managerId;

	public ManagerITests() {
		RestAssured.basePath = basePath + ENDPOINT;
	}

	// GET
	@Test
	public void listOfManagers() {
		given().when().get().then().statusCode(200);
	}

	@Test
	public void getManagerById() {
		given().when().get("/203").then().statusCode(200);
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

	private void createManager() {
		Manager manager = dummyManager();
		String locationHeader = createResource(manager, REGISTER_PATH);
		Manager m = getResource(locationHeader, Manager.class);
		managerId = "/" + m.getManagerId();
		assertEquals(m.getEmail(), manager.getEmail());
	}

	private void createManagerConflict() {
		given().contentType("application/json").body(dummyManager()).when().post(REGISTER_PATH).then().statusCode(409);
	}

	private void updateManager() {
		Manager manager = dummyManager();

		manager.setFirstName(manager.getFirstName() + " updated");
		manager.setLastName(manager.getLastName() + " updated");
		manager.setCompanyName(manager.getCompanyName() + " updated");
		manager.setPhone(manager.getPhone() + " updated");

		String locationHeader = updateResource(managerId, manager);
		Manager resultManager = getResource(locationHeader, Manager.class);

		assertEquals(resultManager.getFirstName().contains("updated"), true);
		assertEquals(resultManager.getLastName().contains("updated"), true);
		assertEquals(resultManager.getCompanyName().contains("updated"), true);
		assertEquals(resultManager.getPhone().contains("updated"), true);
	}

	private void deleteManager() {
		deleteResource(managerId);
		checkResourceNotFound(managerId);
	}

}
