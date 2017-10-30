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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.PersonVehicleMapper;
import com.vehicleman.backend.entities.Vehicle;

import io.swagger.annotations.Api;

@Path("vehicles")
@Api(value = "Vehicles")
public class VehicleService {

	protected VehicleDAO vehicleDao = new VehicleDAO();
	protected PersonDAO personDao = new PersonDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVehicles() {

		List<Vehicle> vehicles = vehicleDao.getVehicles();

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(vehicles)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVehicle(@PathParam("id") int id) {

		Vehicle vehicle = vehicleDao.getVehicle(id);
		if (vehicle == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Vehicle with id: " + id + " not found\"}").build());
		}

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(vehicle)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return Response.ok().entity(vehicle).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createVehicleForPerson(PersonVehicleMapper pvm) {

		// RETHINK - pvm is waiting a person object and List<Vehicle> vehicles list
		/**
		 * example: { "person": { "personId": "2", "firstName": "Adam", "lastName": "Nagy", "email": "some@test.com" },
		 * "vehicles": [ { "vehicleType": "car", "registrationNumber": "test-plate" } ] }
		 *
		 **/

		if (pvm.getVehicles().isEmpty()) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Vehicle list empty\"}").build());
		}

		// must contain vehicle without id
		// TODO: validate not nullable fields are given
		// create vehicle w/o id and person, empty person object
		// create vehicle w/o id with person, Person id must be given

		Vehicle vehicle = pvm.getVehicles().get(0);
		if (vehicleAlreadyExist(vehicle.getRegistrationNumber())) {
			throw new WebApplicationException(
					Response.status(Response.Status.CONFLICT).entity("{\"error\":\"Vehicle with the registration number"
							+ vehicle.getRegistrationNumber() + " already exists\"}").build());
		}

		Person person = pvm.getPerson();
		if (person != null && containsPerson(person)) {
			vehicle.setPerson(person);
			vehicle.setAssigneeId(String.valueOf(person.getPersonId()));
		} else {
			vehicle.setPerson(null);
			vehicle.setAssigneeId("");
		}

		vehicleDao.createVehicle(vehicle);

		// Check if all necessary vehicle fields are given

		return Response.status(Response.Status.CREATED).entity("{\"message\":\"Vehicle successfully created\"}")
				.header(HttpHeaders.LOCATION, "http://localhost:8082/vehicleman/api/vehicles/" + vehicle.getVehicleId())
				.build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateVehicle(@PathParam("id") int id, Vehicle vehicle) {

		// TODO: must pass person vehicle mapper (pvm) to be able to update its person (at least id)
		// TODO: vehicle must save assigned person id

		Vehicle veh = vehicleDao.getVehicle(id);
		if (veh == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Vehicle with id: " + id + " not found\"}").build());
		}

		vehicle.setVehicleId(id);

		// New code
		Person person;

		/**
		 * if assignee id is not empty -> case 1: person exists -> case 2: person id is invalid
		 *
		 * if assignee id is empty -> set person to null
		 */
		if (!vehicle.getAssigneeId().equals("")) {
			int personId = Integer.parseInt(vehicle.getAssigneeId());
			person = personDao.getPerson(personId);
			vehicle.setPerson(person);

			if (person == null) {
				vehicle.setAssigneeId("");
			}
		} else {
			person = null;
		}

		vehicleDao.updateVehicle(vehicle);

		return Response.ok().entity("{\"message\":\"Vehicle with id: " + id + " updated successfully\"}")
				.header(HttpHeaders.LOCATION, "http://localhost:8082/vehicleman/api/vehicles/" + id).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteVehicle(@PathParam("id") int id) {

		Vehicle veh = vehicleDao.getVehicle(id);
		if (veh == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Vehicle with id: " + id + " not found\"}").build());
		}

		vehicleDao.deleteVehicle(id);

		// does not print out response :/
		return Response.status(204).entity("{\"message\":\"Vehicle with id: " + id + " deleted successfully\"}")
				.build();
	}

	// HELPERS

	private boolean vehicleAlreadyExist(String registrationNumber) {
		return vehicleDao.getVehicleByRegistrationNumber(registrationNumber) != null;
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
