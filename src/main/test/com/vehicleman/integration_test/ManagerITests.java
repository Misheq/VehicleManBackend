package com.vehicleman.integration_test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.vehicleman.backend.entities.Manager;

public class ManagerITests extends BaseITest {

	public static String managerId;

	public ManagerITests() {
		RestAssured.basePath = basePath + "/managers";
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
		String locationHeader = createResource(manager);
		Manager m = getResource(locationHeader, Manager.class);
		managerId = "/" + m.getManagerId();
		assertEquals(m.getEmail(), manager.getEmail());
	}

	public void createManagerConflict() {
		given().contentType("application/json").body(dummyManager()).when().post().then().statusCode(409);
	}

	public void updateManager() {
		Manager manager = dummyManager();

		manager.setCompanyName(manager.getCompanyName() + " updated");
		manager.setEmail(manager.getEmail() + " updated");
		manager.setFirstName(manager.getFirstName() + " updated");
		manager.setLastName(manager.getLastName() + " updated");
		manager.setPhone(manager.getPhone() + " updated");

		String locationHeader = updateResource(managerId, manager);
		Manager resultManager = getResource(locationHeader, Manager.class);

		assertEquals(resultManager.getEmail().contains("updated"), true);
		assertEquals(resultManager.getFirstName().contains("updated"), true);
		assertEquals(resultManager.getLastName().contains("updated"), true);
		assertEquals(resultManager.getCompanyName().contains("updated"), true);
		assertEquals(resultManager.getPhone().contains("updated"), true);
	}

	public void deleteManager() {
		deleteResource(managerId);
		checkResourceNotFound(managerId);
	}

}
