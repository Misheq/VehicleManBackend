package com.vehicleman.unit_test.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Manager;
import com.vehicleman.unit_test.base.ManagerTestBase;

public class ManagerDeleteTests extends ManagerTestBase {

	@Test
	public void deleteManagerServiceOK() {
		int id = 1;

		Manager manager = new Manager();
		manager.setManagerId(id);

		when(managerDaoMock.getManager(id)).thenReturn(manager);

		doNothing().when(managerDaoMock).deleteManager(id);

		Response response = target(ENDPOINT + id).request().delete();

		verify(managerDaoMock).getManager(id);
		verify(managerDaoMock).deleteManager(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void deleteManagerServiceNotFound() {
		int id = -1;

		when(managerDaoMock.getManager(id)).thenReturn(null).thenThrow(new NotFoundException());

		Response response = target(ENDPOINT + id).request().delete();

		verify(managerDaoMock).getManager(id);
		verify(managerDaoMock, never()).deleteManager(id);

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
