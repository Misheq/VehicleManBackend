package com.vehicleman.backend.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.PersonVehicleMapper;
import com.vehicleman.backend.entities.Vehicle;

@Path("mapper")
public class PersonVehicleMapperService {

	VehicleDAO vhDAO = new VehicleDAO();
	PersonDAO pDAO = new PersonDAO();

	@Path("assign")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createVehicle(PersonVehicleMapper pvm) {
		
		createMissingEntityAndMapWithExisting(pvm);

		return Response.ok().build();
	}

	private void createMissingEntityAndMapWithExisting(PersonVehicleMapper pvm) {
		for (Vehicle v : pvm.getVehicles()) {
			v.setPerson(pvm.getPerson());
			vhDAO.updateVehicle(v);
		}
	}

//	private boolean containsPerson(Person person) {
//		List<Person> persons = pDAO.getPersons();
//		for (Person p : persons) {
//			if (p.getPersonId() == person.getPersonId()) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean containsVehicle(Vehicle vehicle) {
//		List<Vehicle> vehicles = vhDAO.getVehicles();
//		for (Vehicle v : vehicles) {
//			if (v.getVehicleId() == vehicle.getVehicleId()) {
//				return true;
//			}
//		}
//		return false;
//	}
}
