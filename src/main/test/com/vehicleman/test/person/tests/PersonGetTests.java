package com.vehicleman.test.person.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.test.person.base.PersonTestBase;

public class PersonGetTests extends PersonTestBase {

	List<Person> personList;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		Person p2 = new Person();
		p2.setCompanyName("OOP Systems");
		p2.setEmail("info@oopsystems.com");
		p2.setFirstName("Martin");
		p2.setLastName("Flut");
		p2.setPhone("+3618897898");
		p2.setPersonId(2);

		personList = Arrays.asList(person, p2);
	}

	@Override
	public void tearDown() throws Exception {
		personList = null;

		super.tearDown();
	}

	@Test
	public void getPersonsService() {

		assertEquals(2, personList.size());

		when(personDaoMock.getPersons()).thenReturn(personList);

		Response response = target("persons").request().get();

		verify(personDaoMock).getPersons();

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void getPersonByIdService() {
		int id = 1;

		assertEquals(2, personList.size());

		when(personDaoMock.getPerson(id)).thenReturn(person);

		Response response = target("persons/" + id).request().get();

		verify(personDaoMock).getPerson(id);

		String result = response.readEntity(String.class);
		System.out.println(result);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		try {
			Person p = new ObjectMapper().readValue(result, Person.class);
			assertEquals(p, person);
		} catch (Exception e) {
		}
	}

	@Test
	public void getPersonByIdBadRequest() {
		int id = -1;

		when(personDaoMock.getPerson(id)).thenReturn(null).thenThrow(new BadRequestException());

		Response response = target("persons/" + id).request().get();

		verify(personDaoMock).getPerson(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

}
