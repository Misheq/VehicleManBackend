package com.vehicleman.unit_test.base;

import static org.mockito.Mockito.mock;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.services.VehicleService;

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

public class VehicleTestBase extends JerseyTest {

	public static final String ENDPOINT = "vehicles/";

	protected PersonDAO personDaoMock;
	protected VehicleDAO vehicleDaoMock;

	@Override
	protected Application configure() {
		VehicleServiceMock vehicleServiceMock = new VehicleServiceMock();
		personDaoMock = vehicleServiceMock.getPersonDaoMock();
		vehicleDaoMock = vehicleServiceMock.getVehicleDaoMock();
		return new ResourceConfig().register(vehicleServiceMock);
	}

}
