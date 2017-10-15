package com.vehicleman.unit_test.services;

import static com.jayway.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class ManagerServiceTest {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8082;
		RestAssured.basePath = "/vehicleman/api";
	}

	@Test
	public void getManagersInformation() {

		Response response = given().when().get("/managers");

		System.out.println(response.body().prettyPrint());

		// Validate the status code
		given().when().get("/managers").then().statusCode(200);

	}
}
