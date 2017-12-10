package com.vehicleman.unit_test.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.unit_test.base.PersonTestBase;

public class PersonPostTests extends PersonTestBase {

	@Test
	public void createPersonServiceWithoutVehicle() {

		when(personDaoMock.createPerson(person)).thenReturn(person);

		Response resp = postPerson(person);

		verify(personDaoMock).createPerson(person);
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	public void createPersonServiceWithOneVehicle() {

		vehicleList.add(v1);
		person.setVehicles(vehicleList);

		assertEquals(1, vehicleList.size());
		assertEquals(1, person.getVehicles().size());

		when(personDaoMock.createPerson(person)).thenReturn(person);
		doNothing().when(vehicleDaoMock).updateVehicle(any(Vehicle.class));

		Response resp = postPerson(person);

		verify(personDaoMock, times(1)).createPerson(person);
		verify(vehicleDaoMock, times(1)).updateVehicle(any(Vehicle.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

	@Test
	public void createPersonServiceNotFound() {

		Response resp = target("persons").request().post(null);

		verify(personDaoMock, never()).createPerson(any(Person.class));
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	}

	public Response postPerson(Person person) {
		return target("persons").request().post(Entity.entity(person, MediaType.APPLICATION_JSON));
	}
}
