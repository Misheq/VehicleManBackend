package com.vehicleman.unit_test.base;

import java.util.ArrayList;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;

public class PersonTestBase extends JerseyTest {

	protected PersonDAO personDaoMock;
	protected VehicleDAO vehicleDaoMock;

	protected Person person;
	protected ArrayList<Vehicle> vehicleList;
	protected Vehicle v1, v2;

	@Override
	protected Application configure() {
		PersonServiceMock personServiceMock = new PersonServiceMock();
		personDaoMock = personServiceMock.getPersonDaoMock();
		vehicleDaoMock = personServiceMock.getVehicleDaoMock();
		return new ResourceConfig().register(personServiceMock);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		person = new Person();
		person.setFirstName("joe");
		person.setLastName("bizer");
		person.setEmail("joe@bizer.com");
		person.setCompanyName("JOE'S FACTORY");
		person.setPhone("+4216789978");
		person.setPersonId(1);

		vehicleList = new ArrayList<>();

		v1 = new Vehicle();
		v1.setPerson(person);
		v1.setRegistrationNumber("ABC-123");
		v1.setVehicleId(1);
		v1.setVehicleType("car");

		v2 = new Vehicle();
		v2.setPerson(person);
		v2.setRegistrationNumber("CBA-333");
		v2.setVehicleId(2);
		v2.setVehicleType("track");
	}

	@Override
	public void tearDown() throws Exception {
		person = null;
		vehicleList = null;
		v1 = v2 = null;

		super.tearDown();
	}

}
