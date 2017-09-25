package com.vehicleman.backend.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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
	public Response getVehicles() {

		List<Vehicle> vehicles = vehicleDao.getVehicles();

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(vehicles)).build();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVehicle(@PathParam("id") int id) {

		Vehicle veh = vehicleDao.getVehicle(id);
		if(veh == null) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Vehicle with id: " + id + " not found\"}").build());
		}

		return Response.ok().entity(veh).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createVehicleForPerson(PersonVehicleMapper pvm) {

		// RETHINK - pvm is waiting a person object and List<Vehicle> vehicles list
		/**
		 * example: { "person": { "personId": "2", "firstName": "Adam",
		 * "lastName": "Nagy", "email": "some@test.com" }, "vehicles": [ {
		 * "vehicleType": "car", "registrationNumber": "test-plate" } ] }
		 *
		 **/

		// must contain vehicle without id
		// TODO: validate not nullable fields are given
		// create vehicle w/o id and person, empty person object
		// create vehicle w/o id with person, Person id must be given
		Vehicle v = pvm.getVehicles().get(0);
		if(containsPerson(pvm.getPerson())) {
			v.setPerson(pvm.getPerson());
		} else {
			v.setPerson(null);
		}

		vehicleDao.createVehicle(v);

		// Check if all necessary vehicle fields are given

		return Response.ok().entity("{\"message\":\"Vehicle successfully created\"}").build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateVehicle(@PathParam("id") int id, Vehicle vehicle) {

		Vehicle veh = vehicleDao.getVehicle(id);
		if(veh == null) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Vehicle with id: " + id + " not found\"}").build());
		}

		vehicle.setVehicleId(id);
		vehicleDao.updateVehicle(vehicle);

		return Response.ok().entity("{\"message\":\"Vehicle with id: " + id + " updated successfully\"}").build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteVehicle(@PathParam("id") int id) {

		Vehicle veh = vehicleDao.getVehicle(id);
		if(veh == null) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Vehicle with id: " + id + " not found\"}").build());
		}

		vehicleDao.deleteVehicle(id);

		// does not print out response :/
		return Response.status(204).entity("{\"message\":\"Vehicle with id: " + id + " deleted successfully\"}").build();
	}

	// HELPERS

	private boolean containsPerson(Person person) {
		List<Person> persons = personDao.getPersons();
		for(Person p : persons) {
			if(p.getPersonId() == person.getPersonId()) {
				return true;
			}
		}
		return false;
	}

}
