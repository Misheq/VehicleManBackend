package com.vehicleman.unit_test.vehicle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.services.VehicleService;
import com.vehicleman.unit_test.base.VehicleTestBase;

class VehicleServiceMock extends VehicleService {

	public VehicleServiceMock() {
		super.personDao = mock(PersonDAO.class);
		super.vehicleDao = mock(VehicleDAO.class);
	}

	protected PersonDAO getPersonDaoMock() {
		return personDao;
	}

	protected VehicleDAO getVehicleDaoMock() {
		return vehicleDao;
	}
}

public class VehicleGetTests extends VehicleTestBase {

	@Test
	public void getVehicleService() {

		ArrayList<Vehicle> vehicleList = new ArrayList<>();
		vehicleList.add(new Vehicle());
		vehicleList.add(new Vehicle());

		assertEquals(2, vehicleList.size());

		when(vehicleDaoMock.getVehicles()).thenReturn(vehicleList);

		Response response = target("vehicles").request().get();

		verify(vehicleDaoMock).getVehicles();

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void getVehicleByIdService() {
		int id = 1;

		ArrayList<Vehicle> vehicleList = new ArrayList<>();
		vehicleList.add(new Vehicle());
		vehicleList.add(new Vehicle());

		assertEquals(2, vehicleList.size());

		when(vehicleDaoMock.getVehicle(id)).thenReturn(vehicleList.get(id - 1));

		Response response = target("vehicles/" + id).request().get();

		verify(vehicleDaoMock).getVehicle(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		// or more detailed
		/*
		 * String result = response.readEntity(String.class); System.out.println(result);
		 * assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		 * 
		 * try { Person p = new ObjectMapper() .readValue(result, Person.class); assertEquals(p, person); } catch
		 * (Exception e) { }
		 */
	}

	@Test
	public void getVehicleByIdServiceNotFound() {
		int id = -1;

		when(vehicleDaoMock.getVehicle(id)).thenReturn(null).thenThrow(new NotFoundException());

		Response response = target("vehicles/" + id).request().get();

		verify(vehicleDaoMock).getVehicle(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
