package com.vehicleman.unit_test.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Manager;
import com.vehicleman.unit_test.base.ManagerTestBase;

public class ManagerGetTests extends ManagerTestBase {

	@Test
	public void getManagerService() {

		when(managerDaoMock.getManagers()).thenReturn(new ArrayList<Manager>());

		Response response = target(ENDPOINT).request().get();

		verify(managerDaoMock).getManagers();

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void getManagerById() {
		int id = 1;
		Manager manager = new Manager();
		manager.setManagerId(id);

		when(managerDaoMock.getManager(id)).thenReturn(manager);

		Response response = target(ENDPOINT + id).request().get();

		verify(managerDaoMock).getManager(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void getManagerByIdNotFound() {
		int id = -1;

		when(managerDaoMock.getManager(id)).thenReturn(null).thenThrow(new NotFoundException());

		Response response = target(ENDPOINT + id).request().get();

		verify(managerDaoMock).getManager(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
