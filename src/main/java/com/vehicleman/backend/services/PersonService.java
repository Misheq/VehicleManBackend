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
import com.vehicleman.backend.entities.Vehicle;

// import io.swagger.annotations.Api;

@Path("persons")
// @Api(value = "Persons")
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

		// should be bad request or malformed
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

		// should be bad request?
		return Response.ok().entity(person).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createPerson(Person person) {

		validatePerson(person);
		personWithEmailAlreadyExist(person);

		person = personDao.createPerson(person);

		if (personHasVehicle(person)) {
			setVehicleForPerson(person);
		}

		return Response.status(Response.Status.CREATED).entity("{\"message\":\"Person created successfully\"}")
				.header(HttpHeaders.LOCATION, "http://localhost:8082/vehicleman/api/persons/").build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePerson(@PathParam("id") int id, Person person) {

		Person per = personDao.getPerson(id);
		validatePerson(per);

		person.setPersonId(id);

		// if user updated email
		if (emailChanged(person)) {
			personWithEmailAlreadyExist(person);
		}

		// always check if person had vehicle before update
		Person personBeforeUpdate = personDao.getPerson(id);
		if (personHasVehicle(personBeforeUpdate)) {
			removeVehicleFromPerson(personBeforeUpdate);
		}

		if (personHasVehicle(person)) {
			setVehicleForPerson(person);
		}

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

		if (personHasVehicle(person)) {
			removeVehicleFromPerson(person);
		}

		personDao.deletePerson(id);

		return Response.noContent().entity("{\"message\":\"Person with id: " + id + " deleted successfully\"}").build();
	}

	////////////////////////// HELPERS ///////////////////////////////////////

	private void personWithEmailAlreadyExist(Person person) {
		if (personAlreadyExist(person.getEmail())) {
			throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
					.entity("{\"error\":\"Person with the email " + person.getEmail() + " already exists\"}").build());
		}
	}

	private boolean emailChanged(Person person) {
		Person oldPerson = personDao.getPerson(person.getPersonId());
		return !oldPerson.getEmail().equals(person.getEmail());
	}

	private boolean personAlreadyExist(String email) {
		return personDao.getPersonByEmail(email) != null;
	}

	private void validatePerson(Person person) {
		if (person == null) {
			throw new NotFoundException(
					Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Person not found\"}").build());
		}
	}

	private void setVehicleForPerson(Person person) {
		Vehicle vehicle = new Vehicle();
		vehicle = person.getVehicles().get(0);
		vehicle.setPerson(person);
		vehicle.setAssigneeId(String.valueOf(person.getPersonId()));
		vehicleDao.updateVehicle(vehicle);
	}

	private void removeVehicleFromPerson(Person person) {
		Vehicle vehicle = new Vehicle();
		vehicle = person.getVehicles().get(0);
		vehicle.setPerson(null);
		vehicle.setAssigneeId("");
		vehicleDao.updateVehicle(vehicle);
	}

	private boolean personHasVehicle(Person p) {
		return p.getVehicles().size() != 0;
	}

}
