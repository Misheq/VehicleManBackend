package com.vehicleman.test.person.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Person;
import com.vehicleman.test.person.base.PersonTestBase;

public class PersonPutTests extends PersonTestBase {

	@Test
	public void updatePersonServiceOK() {
		int id = 1;

		when(personDaoMock.getPerson(id)).thenReturn(person);

		Response response = target("persons/" + id).request().put(Entity.entity(person, MediaType.APPLICATION_JSON));

		verify(personDaoMock).getPerson(id);
		verify(personDaoMock).updatePerson(person);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void updatePersonServiceNotFound() {
		int id = -1;

		when(personDaoMock.getPerson(id)).thenReturn(null).thenThrow(new NotFoundException());

		Response response = target("persons/" + id).request()
				.put(Entity.entity(new Person(), MediaType.APPLICATION_JSON));

		verify(personDaoMock).getPerson(id);
		verify(personDaoMock, never()).updatePerson(person);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}
