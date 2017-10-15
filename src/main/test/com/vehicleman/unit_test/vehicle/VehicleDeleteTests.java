package com.vehicleman.unit_test.vehicle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.unit_test.base.VehicleTestBase;

public class VehicleDeleteTests extends VehicleTestBase {

	@Test
	public void deleteVehicleService() {
		int id = 1;

		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleId(id);

		when(vehicleDaoMock.getVehicle(id)).thenReturn(vehicle);

		Response response = target(ENDPOINT + id).request().delete();

		verify(vehicleDaoMock).getVehicle(id);
		verify(vehicleDaoMock).deleteVehicle(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void deleteVehicleServiceNotFound() {
		int id = -1;

		when(vehicleDaoMock.getVehicle(id)).thenReturn(null).thenThrow(new NotFoundException());

		Response response = target(ENDPOINT + id).request().delete();

		verify(vehicleDaoMock).getVehicle(id);
		verify(vehicleDaoMock, never()).deleteVehicle(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
