package com.vehicleman.unit_test.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.services.PersonService;

public class PeopleServiceTests extends JerseyTest {

	List<Person> personsList;
	PersonDAO pdao;
	PersonService ps;

	// @Before
	// public void setup() {
	// this.personsList = new ArrayList<Person>();
	// this.pdao = mock(PersonDAO.class);
	// this.ps = mock(PersonService.class);
	//
	// setupListWithPersons();
	// }

	public void setupListWithPersons() {

		Person p1 = new Person();
		p1.setCompanyName("cname");
		p1.setEmail("some@email.com");
		p1.setFirstName("firstNAme");
		p1.setLastName("klastnane");
		p1.setPhone("phonenumber");
		p1.setPersonId(1);

		Person p2 = new Person();
		p2.setCompanyName("cname");
		p2.setEmail("some@email.com");
		p2.setFirstName("firstNAme");
		p2.setLastName("klastnane");
		p2.setPhone("phonenumber");
		p2.setPersonId(2);

		personsList.add(p1);
		personsList.add(p2);
	}

	// @Test
	// public void testGetAllPersonsService() {
	//
	// when(pdao.getPersons()).thenReturn(personsList);
	//
	// ObjectMapper om = new ObjectMapper();
	//
	// try {
	// when(ps.getPersons())
	// .thenReturn(Response.ok().entity(om.writeValueAsString(personsList)).build());
	// } catch (JsonProcessingException e) {
	// e.printStackTrace();
	// }
	//
	// List<Person> list = pdao.getPersons();
	//
	// assertEquals(2, list.size());
	// assertEquals(200, ps.getPersons().getStatus());
	// }
	//
	// @Test
	// public void getPersonWithIdServiceAndReturnAPerson() {
	// int id = 1;
	// Person person = personsList.get(id);
	// when(pdao.getPerson(id)).thenReturn(person);
	// try {
	// when(ps.getPerson(id))
	// .thenReturn(
	// Response.
	// ok().
	// entity(new ObjectMapper().
	// writeValueAsString(person))
	// .build()
	// );
	// } catch(Exception e) {
	//
	// }
	//
	// assertEquals(person, pdao.getPerson(id));
	// assertEquals(200, ps.getPerson(id).getStatus());
	// }
	//
	// @Test
	// public void getPersonWithIdServiceAndReturnNotFound() {
	// int id = 3;
	// when(pdao.getPerson(id)).thenReturn(null);
	// try {
	// when(ps.getPerson(id))
	// .thenReturn(
	// Response
	// .status(404)
	// .entity("{\"error\":\"Person with id: " + id + " not found\"}")
	// .build()
	// );
	// } catch(Exception e) {
	//
	// }
	// assertEquals(null, pdao.getPerson(id));
	// assertEquals(404, ps.getPerson(id).getStatus());
	// }

	@Override
	protected Application configure() {

		return new ResourceConfig().register(PersonService.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		this.personsList = new ArrayList<Person>();
		this.ps = Mockito.mock(PersonService.class);
		this.pdao = mock(PersonDAO.class);

		setupListWithPersons();
	}

	@Test
	public void someTestA() {
		when(pdao.getPersons()).thenReturn(personsList);
		try {
			when(ps.getPersons())
					.thenReturn(Response.ok().entity(new ObjectMapper().writeValueAsString(personsList)).build());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// UriInfo uriInfo = Mockito.mock(UriInfo.class);
		// when(uriInfo.getRequestUri()).thenReturn(URI.create("http://localhost:8080/vehicleman/api/"));
		Response r = mock(Response.class);
		// target("persons")
		// .request(MediaType.APPLICATION_JSON_TYPE)
		// .get();
		Response r2;
		try {
			r2 = Response.ok().entity(new ObjectMapper().writeValueAsString(personsList)).build();
			//			when(target("persons")).thenReturn(r2);
		} catch (Exception e) {
		}

		System.out.println("Response: ");
		System.out.println(r.readEntity(String.class));

		// assertEquals(Response.Status.OK.getStatusCode(),
		// response.getStatus());

		// response.close();
	}

}
