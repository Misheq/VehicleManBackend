package com.vehicleman.unit_test.vehicle;

import com.vehicleman.unit_test.base.VehicleTestBase;

public class VehiclePostTests extends VehicleTestBase {

	//	private static final String ENDPOINT = "vehicles";
	//
	//	PersonVehicleMapper pvm;
	//	List<Vehicle> vehicleList;
	//	Vehicle vehicle;
	//
	//	@Override
	//	public void setUp() throws Exception {
	//		super.setUp();
	//
	//		pvm = new PersonVehicleMapper();
	//		vehicleList = pvm.getVehicles();
	//		vehicle = new Vehicle();
	//		//		vehicle = mock(Vehicle.class);
	//	}
	//
	//	@Override
	//	public void tearDown() throws Exception {
	//		pvm = null;
	//		vehicleList = null;
	//		vehicle = null;
	//
	//		super.tearDown();
	//	}
	//
	//	@Test
	//	public void createVehicleServiceWithoutPerson() {
	//
	//		vehicleList.add(vehicle);
	//
	//		assertEquals(1, pvm.getVehicles().size());
	//
	//		doNothing().when(vehicleDaoMock).createVehicle(vehicle);
	//
	//		Response resp = target(ENDPOINT).request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));
	//
	//		verify(vehicleDaoMock).createVehicle(vehicle);
	//		verify(personDaoMock, never()).getPersons();
	//
	//		System.out.println(resp.readEntity(String.class));
	//		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	//	}
	//
	//	@Test
	//	public void createVehicleServiceWithPerson() {
	//		// in the service why not adding just one wehicle
	//		// why is pvm.getVehicles().get(0) needed?
	//		Person p = new Person();
	//		p.setPersonId(1);
	//		pvm.setPerson(p);
	//		vehicle.setPerson(p);
	//		vehicleList.add(vehicle);
	//		pvm.setVehicles(vehicleList);
	//
	//		ArrayList<Person> plist = new ArrayList<>();
	//		plist.add(p);
	//
	//		assertEquals(1, pvm.getVehicles().size());
	//
	//		doNothing().when(vehicleDaoMock).createVehicle(vehicle);
	//		when(personDaoMock.getPersons()).thenReturn(plist);
	//
	//		Response resp = target(ENDPOINT).request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));
	//
	//		verify(vehicleDaoMock).createVehicle(vehicle);
	//		verify(personDaoMock).getPersons();
	//
	//		System.out.println(resp.readEntity(String.class));
	//		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	//	}
	//
	//	@Test
	//	public void createVehicleServiceWithAnotherPerson() {
	//		// in the service why not adding just one wehicle
	//		// why is pvm.getVehicles().get(0) needed?
	//		Person p = new Person();
	//		p.setPersonId(2);
	//		pvm.setPerson(p);
	//		vehicle.setPerson(p);
	//		vehicleList.add(vehicle);
	//		pvm.setVehicles(vehicleList);
	//
	//		ArrayList<Person> plist = new ArrayList<>();
	//		plist.add(p);
	//
	//		assertEquals(1, pvm.getVehicles().size());
	//
	//		doNothing().when(vehicleDaoMock).createVehicle(vehicle);
	//		when(personDaoMock.getPersons()).thenReturn(plist);
	//
	//		Response resp = target(ENDPOINT).request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));
	//
	//		verify(vehicleDaoMock).createVehicle(vehicle);
	//		verify(personDaoMock).getPersons();
	//
	//		System.out.println(resp.readEntity(String.class));
	//		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	//	}
	//
	//	@Test
	//	public void createVehicleServiceNotFound() {
	//
	//		assertEquals(0, pvm.getVehicles().size());
	//
	//		Response resp = target(ENDPOINT).request().post(Entity.entity(pvm, MediaType.APPLICATION_JSON));
	//
	//		verify(vehicleDaoMock, never()).createVehicle(vehicle);
	//		verify(personDaoMock, never()).getPersons();
	//
	//		System.out.println(resp.readEntity(String.class));
	//		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
	//	}
}
