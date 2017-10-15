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

@Path("persons")
@Api(value = "Persons")
public class PersonService {

	protected PersonDAO personDao;
	protected VehicleDAO vehicleDao;

	public PersonService() {
		this.personDao = new PersonDAO();
		this.vehicleDao = new VehicleDAO();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	//	@ApiOperation(value = "Get all persons", notes = "get all persons from the database", response = Person.class, responseContainer = "List")
	public Response getPersons() {

		List<Person> persons = personDao.getPersons();

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(persons)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPerson(@PathParam("id") int id) {

		Person person = personDao.getPerson(id);

		if (person == null) {
			throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
					.entity("{\"error\":\"Person with id: " + id + " not found\"}").build());
		}

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(person)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return Response.ok().entity(person).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createPerson(PersonVehicleMapper pvm) {

		// create person with car -> must add person w/o id and car list (can add many existing cars) w ids
		// create person w/o  car -> must contain person w/o id, car-list can be empty
		if (pvm.getPerson() == null) {
			// for backend validation - on front end should technically not be able to do so
			throw new NotFoundException(
					Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Person not found\"}").build());
		}

		// TODO: validate if not nullable fields are given
		personDao.createPerson(pvm.getPerson());
		// if list is empty does not add anything if list contains cars it will map it together -> on front end cannot be given falsly

		createMissingEntityAndMapWithExisting(pvm);

		// TODO: port should be given dynamically

		return Response.status(Response.Status.CREATED).entity("{\"message\":\"Person created successfully\"}")
				.header(HttpHeaders.LOCATION,
						"http://localhost:8082/vehicleman/api/persons/" + pvm.getPerson().getPersonId())
				.build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePerson(@PathParam("id") int id, Person person) {

		Person per = personDao.getPerson(id);
		if (per == null) {
			throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
					.entity("{\"error\":\"Person with id: " + id + " not found\"}").build());
		}

		person.setPersonId(id);
		personDao.updatePerson(person);

		return Response.ok().entity("{\"message\":\"Person with id: " + id + " updated successfully\"}")
				.header(HttpHeaders.LOCATION, "http://localhost:8082/vehicleman/api/persons/" + id).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deletePerson(@PathParam("id") int id) {

		Person person = personDao.getPerson(id);
		if (person == null) {
			throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
					.entity("{\"error\":\"Person with id: " + id + " not found\"}").build());
		}

		// workaround ----------------------
		detachCarsFromOwner(person);

		// ---------------------------------
		// delete after vehicles removed from list
		personDao.deletePerson(id);

		return Response.noContent().entity("{\"message\":\"Person with id: " + id + " deleted successfully\"}").build();
	}

	// HELPERS

	private void createMissingEntityAndMapWithExisting(PersonVehicleMapper pvm) {

		for (Vehicle v : pvm.getVehicles()) {
			v.setPerson(pvm.getPerson());
			if (containsVehicle(v)) {
				vehicleDao.updateVehicle(v);
			} else {
				vehicleDao.createVehicle(v);
			}
		}
	}

	private void detachCarsFromOwner(Person person) {
		for (Vehicle v : person.getVehicles()) {
			v.setPerson(null);
			vehicleDao.updateVehicle(v);
		}
	}

	private boolean containsVehicle(Vehicle vehicle) {
		List<Vehicle> vehicles = vehicleDao.getVehicles();
		for (Vehicle v : vehicles) {
			if (v.getVehicleId() == vehicle.getVehicleId()) {
				return true;
			}
		}
		return false;
	}
}
