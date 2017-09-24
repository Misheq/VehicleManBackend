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

@Path("persons")
public class PersonService {
	
	PersonDAO personDao = new PersonDAO();
	VehicleDAO vehicleDao = new VehicleDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String getPersons() {
		
		List<Person> persons = personDao.getPersons();
		
		ObjectMapper om = new ObjectMapper();
		
		try {
			return om.writeValueAsString(persons);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Person getPerson(@PathParam("id") int id) {
		
		return personDao.getPerson(id);
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createPersonForVehicle(PersonVehicleMapper pvm) {
		
		// must contain vehicle without id

		Vehicle v = pvm.getVehicles().get(0);
		if(containsPerson(pvm.getPerson())) {
			v.setPerson(pvm.getPerson());
		} else {
			v.setPerson(null);
		}
		
		vehicleDao.createVehicle(v);

		return Response.ok().build();
	}
	
	@PUT	
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Person updatePerson(@PathParam("id") int id, Person person) {
		
		person.setPersonId(id);
		personDao.updatePerson(person);
		
		return person;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deletePerson(@PathParam("id") int id) {

		personDao.deletePerson(id);			
		
		return Response.noContent().build();
	}
	
	// HELPERS
	
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
