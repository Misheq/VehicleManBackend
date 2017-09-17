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

import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.entities.Person;

@Path("persons")
public class PersonService {
	
	PersonDAO personDao = new PersonDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Person> getPersons() {
		
		List<Person> persons = personDao.getPersons();
		
		return persons;
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Person getPerson(@PathParam("id") String id) {
		
		return personDao.getPerson(id);
	}
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Person createPerson(Person person) {
		
		personDao.createPerson(person);
		
		// return response code and message and maybe created entity
		return person;
	}
	
	@PUT	
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Person updatePerson(@PathParam("id") String id, Person person) {
		
		person.setId(id);
		personDao.updatePerson(person);
		
		return person;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deletePerson(@PathParam("id") String id) {

		personDao.deletePerson(id);			
		
		return Response.noContent().build();
	}
}
