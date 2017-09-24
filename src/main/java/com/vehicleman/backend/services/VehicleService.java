package com.vehicleman.backend.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.PersonVehicleMapper;
import com.vehicleman.backend.entities.Vehicle;

@Path("vehicles")
public class VehicleService {

	VehicleDAO vehicleDao = new VehicleDAO();
	PersonDAO personDao = new PersonDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String getVehicles() {

		List<Vehicle> vehicles = vehicleDao.getVehicles();

		ObjectMapper om = new ObjectMapper();

		try {
			return om.writeValueAsString(vehicles);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Vehicle getVehicle(@PathParam("id") int id) {

		return vehicleDao.getVehicle(id);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createVehicleForPerson(PersonVehicleMapper pvm) {

		if (!containsPerson(pvm.getPerson())) {
			personDao.createPerson(pvm.getPerson());
			createMissingEntityAndMapWithExisting(pvm);
		}

		return Response.ok().build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Vehicle updateVehicle(@PathParam("id") int id, Vehicle vehicle) {

		vehicle.setVehicleId(id);
		vehicleDao.updateVehicle(vehicle);

		return vehicle;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteVehicle(@PathParam("id") int id) {

		vehicleDao.deleteVehicle(id);

		return Response.noContent().build();
	}
	
	// HELPERS
	
	private void createMissingEntityAndMapWithExisting(PersonVehicleMapper pvm) {
		for (Vehicle v : pvm.getVehicles()) {
			v.setPerson(pvm.getPerson());
			vehicleDao.updateVehicle(v);
		}
	}

	private boolean containsPerson(Person person) {
		List<Person> persons = personDao.getPersons();
		for (Person p : persons) {
			if (p.getPersonId() == person.getPersonId()) {
				return true;
			}
		}
		return false;
	}

}
