package com.vehicleman.unit_test.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Manager;
import com.vehicleman.unit_test.base.ManagerTestBase;

public class ManagerPutTests extends ManagerTestBase {

	@Test
	public void updateManagerServiceOK() {
		int id = 1;

		Manager manager = new Manager();
		manager.setManagerId(id);

		when(managerDaoMock.getManager(id)).thenReturn(manager);

		Response response = target(ENDPOINT + id).request().put(Entity.entity(manager, MediaType.APPLICATION_JSON));

		verify(managerDaoMock).getManager(id);
		// again, arguments are different
		//		verify(managerDaoMock).updateManager(manager);
		verify(managerDaoMock).updateManager(any(Manager.class));

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void updateManagerServiceNotFound() {
		int id = -1;

		when(managerDaoMock.getManager(id)).thenReturn(null).thenThrow(new NotFoundException());

		Response response = target(ENDPOINT + id).request()
				.put(Entity.entity(new Manager(), MediaType.APPLICATION_JSON));

		verify(managerDaoMock).getManager(id);
		verify(managerDaoMock, never()).updateManager(any(Manager.class));

		System.out.println(response.readEntity(String.class));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

}
