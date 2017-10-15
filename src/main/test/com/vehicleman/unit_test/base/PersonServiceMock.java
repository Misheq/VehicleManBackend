package com.vehicleman.unit_test.base;

import static org.mockito.Mockito.mock;

import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.services.PersonService;

class PersonServiceMock extends PersonService {

	public PersonServiceMock() {
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
