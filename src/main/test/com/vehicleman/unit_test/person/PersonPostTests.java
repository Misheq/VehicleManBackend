package com.vehicleman.unit_test.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.PersonVehicleMapper;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.unit_test.base.PersonTestBase;

public class PersonPostTests extends PersonTestBase {

	PersonVehicleMapper pvm;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		pvm = new PersonVehicleMapper();
		pvm.setPerson(person);
	}

	@Override
	public void tearDown() throws Exception {
		pvm = null;

		super.tearDown();
	}

	@Test
	public void createPersonServiceWithoutVehicle() {

		doNothing().when(personDaoMock).createPerson(pvm.getPerson());
		doNothing().when(vehicleDaoMock).updateVehicle(any(Vehicle.class));

		Response resp = target("persons").request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));

		verify(personDaoMock).createPerson(person);
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	public void createPersonServiceWithOneVehicle() {

		vehicleList.add(v1);
		pvm.setVehicles(vehicleList);

		assertEquals(1, pvm.getVehicles().size());

		doNothing().when(personDaoMock).createPerson(pvm.getPerson());
		doNothing().when(vehicleDaoMock).updateVehicle(any(Vehicle.class));

		Response resp = target("persons").request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));

		verify(personDaoMock, times(1)).createPerson(person);
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));
		verify(vehicleDaoMock, times(1)).createVehicle(any(Vehicle.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	public void createPersonServiceWithTwoVehicles() {

		vehicleList.add(v1);
		vehicleList.add(v2);
		pvm.setVehicles(vehicleList);

		assertEquals(2, pvm.getVehicles().size());

		doNothing().when(personDaoMock).createPerson(pvm.getPerson());
		doNothing().when(vehicleDaoMock).updateVehicle(any(Vehicle.class));

		Response resp = target("persons").request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));

		verify(personDaoMock, times(1)).createPerson(person);
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));
		verify(vehicleDaoMock, times(2)).createVehicle(any(Vehicle.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	public void createPersonServiceNotFound() {

		pvm.setPerson(null);

		Response resp = target("persons").request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));

		verify(personDaoMock, never()).createPerson(person);
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}
}
