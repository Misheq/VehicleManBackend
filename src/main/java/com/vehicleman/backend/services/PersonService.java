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

@Path("persons")
public class PersonService {

	PersonDAO personDao = new PersonDAO();
	VehicleDAO vehicleDao = new VehicleDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPersons() {

		List<Person> persons = personDao.getPersons();

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(persons)).build();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPerson(@PathParam("id") int id) {

		Person pers = personDao.getPerson(id);

		if(pers == null) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Person with id: " + id + " not found\"}").build());
		}

		return Response.ok().entity(pers).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createPerson(PersonVehicleMapper pvm) {

		// create person with car -> must add person w/o id and car list (can add many existing cars) w ids
		// create person w/o  car -> must contain person w/o id, car-list can be empty
		if(pvm.getPerson() == null) {
			// for backend validation - on front end should technically not be able to do so
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Person not found\"}").build());
		}

		// TODO: validate if not nullable fields are given
		personDao.createPerson(pvm.getPerson());
		// if list is empty does not add anything if list contains cars it will map it together -> on front end cannot be given falsly

		// if existing car added -> creates person + assigns him at vehicle table on db level, but error while
		// writing it out serializa
		/**
		 *
		 * at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(
		 * BeanSerializer.java:155) at
		 * com.fasterxml.jackson.databind.ser.std.CollectionSerializer.
		 * serializeContents(CollectionSerializer.java:149) at
		 * com.fasterxml.jackson.databind.ser.std.CollectionSerializer.serialize
		 * (CollectionSerializer.java:112) at
		 * com.fasterxml.jackson.databind.ser.std.CollectionSerializer.serialize
		 * (CollectionSerializer.java:25)
		 *
		 *
		 */

		createMissingEntityAndMapWithExisting(pvm);

		return Response.ok().entity("{\"message\":\"Person created successfully\"}").build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updatePerson(@PathParam("id") int id, Person person) {

		Person per = personDao.getPerson(id);
		if(per == null) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Person with id: " + id + " not found\"}").build());
		}

		person.setPersonId(id);
		personDao.updatePerson(person);

		return Response.ok().entity("{\"message\":\"Person with id: " + id + " updated successfully\"}").build();
	}

	@DELETE
	@Path("/{id}")
	public Response deletePerson(@PathParam("id") int id) {

		Person pers = personDao.getPerson(id);
		if(pers == null) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Person with id: " + id + " not found\"}").build());
		}

		personDao.deletePerson(id);

		return Response.noContent().entity("{\"message\":\"Person with id: " + id + " deleted successfully\"}").build();
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

	private void createMissingEntityAndMapWithExisting(PersonVehicleMapper pvm) {
		for(Vehicle v : pvm.getVehicles()) {
			v.setPerson(pvm.getPerson());
			vehicleDao.updateVehicle(v);
		}
	}
}
