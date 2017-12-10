package com.vehicleman.unit_test.vehicle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.unit_test.base.VehicleTestBase;

public class VehiclePostTests extends VehicleTestBase {

	List<Vehicle> vehicleList;
	Vehicle vehicle;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		vehicle = new Vehicle();
		vehicleList = new ArrayList<>();
	}

	@Override
	public void tearDown() throws Exception {
		vehicleList = null;
		vehicle = null;

		super.tearDown();
	}

	@Test
	public void createVehicleServiceWithoutPerson() {

		doNothing().when(vehicleDaoMock).createVehicle(vehicle);

		Response resp = target(ENDPOINT).request().post(Entity.entity(vehicle, MediaType.APPLICATION_JSON));

		verify(vehicleDaoMock).createVehicle(vehicle);
		verify(personDaoMock, never()).getPersons();

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	public void createVehicleServiceWithPerson() {

		Person person = new Person();
		person.setPersonId(1);

		vehicle.setPerson(person);

		assertEquals(person, vehicle.getPerson());

		doNothing().when(vehicleDaoMock).createVehicle(vehicle);

		Response resp = target(ENDPOINT).request().post(Entity.entity(vehicle, MediaType.APPLICATION_JSON));

		verify(vehicleDaoMock).createVehicle(vehicle);

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	public void createVehicleServiceNotFound() {

		Response resp = target(ENDPOINT).request().post(null);

		verify(vehicleDaoMock, never()).createVehicle(vehicle);
		verify(personDaoMock, never()).getPersons();

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
	}
}
