package com.vehicleman.unit_test.person;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.unit_test.base.PersonTestBase;

public class PersonDeleteTests extends PersonTestBase {

	@Test
	public void deletePersonServiceWithoutVehicles() {
		int id = 1;

		when(personDaoMock.getPerson(id)).thenReturn(person);

		doNothing().when(personDaoMock).deletePerson(id);

		Response response = target("persons/" + id).request().delete();

		verify(personDaoMock).getPerson(id);
		verify(personDaoMock).deletePerson(id);
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));

		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void deletePersonServiceWithOneVehicle() {
		int id = 1;

		vehicleList.add(v1);
		person.setVehicles(vehicleList);

		when(personDaoMock.getPerson(id)).thenReturn(person);

		doNothing().when(personDaoMock).deletePerson(id);
		doNothing().when(vehicleDaoMock).updateVehicle(any(Vehicle.class));

		Response response = target("persons/" + id).request().delete();

		verify(personDaoMock).getPerson(id);
		verify(personDaoMock).deletePerson(id);
		verify(vehicleDaoMock).updateVehicle(any(Vehicle.class));

		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void deletePersonServiceWithTwoVehicles() {
		int id = 1;

		vehicleList.add(v1);
		vehicleList.add(v2);
		person.setVehicles(vehicleList);

		when(personDaoMock.getPerson(id)).thenReturn(person);

		doNothing().when(personDaoMock).deletePerson(id);
		doNothing().when(vehicleDaoMock).updateVehicle(any(Vehicle.class));

		Response response = target("persons/" + id).request().delete();

		verify(personDaoMock).getPerson(id);
		verify(personDaoMock).deletePerson(id);
		verify(vehicleDaoMock, times(2)).updateVehicle(any(Vehicle.class));

		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void deletePersonServiceNotFound() {
		int id = -1;

		when(personDaoMock.getPerson(id)).thenReturn(null).thenThrow(new BadRequestException());

		Response response = target("persons/" + id).request().delete();

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
