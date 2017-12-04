package com.vehicleman.unit_test.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Manager;
import com.vehicleman.unit_test.base.ManagerTestBase;

public class ManagerPostTests extends ManagerTestBase {

//	@Test
//	public void createManagerService() {
//
//		Manager manager = new Manager();
//		manager.setEmail("manager@email.com");
//
//		when(managerDaoMock.createManager(manager)).thenReturn(manager);
//
//		Response resp = target("auth/register").request()
//				.post(Entity.entity(manager, MediaType.APPLICATION_JSON));
//
//		verify(managerDaoMock).findManagerByEmail(manager.getEmail());
//		verify(managerDaoMock).createManager(any(Manager.class));
//
//		System.out.println(resp.readEntity(String.class));
//		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
//	}

}
