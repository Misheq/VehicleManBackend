package com.vehicleman.unit_test.vehicle;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.unit_test.base.VehicleTestBase;

public class VehiclePutTests extends VehicleTestBase {

	@Test
	public void updateVehicleServiceOK() {
		int id = 1;

		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleId(id);
		vehicle.setRegistrationNumber("REG-NUM");

		when(vehicleDaoMock.getVehicle(id)).thenReturn(vehicle);

		Response response = target(ENDPOINT + id).request().put(Entity.entity(vehicle, MediaType.APPLICATION_JSON));

		verify(vehicleDaoMock, times(2)).getVehicle(id);
		verify(vehicleDaoMock).updateVehicle(vehicle);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void updateVehicleServiceNotFound() {
		int id = -1;

		when(vehicleDaoMock.getVehicle(id)).thenReturn(null).thenThrow(new NotFoundException());

		Response response = target(ENDPOINT + id).request()
				.put(Entity.entity(new Vehicle(), MediaType.APPLICATION_JSON));

		verify(vehicleDaoMock).getVehicle(id);
		verify(vehicleDaoMock, never()).updateVehicle(any(Vehicle.class));

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}
