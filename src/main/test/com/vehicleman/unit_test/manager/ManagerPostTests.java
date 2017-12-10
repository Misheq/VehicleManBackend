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
import com.vehicleman.unit_test.base.AuthTestBase;

public class ManagerPostTests extends AuthTestBase {

	protected Manager manager;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		manager = new Manager();
		manager.setManagerId(1);
		manager.setEmail("manager@email.com");
	}
	
	@Override
	public void tearDown() throws Exception {
		manager = null;

		super.tearDown();
	}
	
	@Test
	public void createManagerService() {
		
		when(managerDaoMock.createManager(manager)).thenReturn(manager);
		when(managerDaoMock.findManagerByEmail(manager.getEmail())).thenReturn(null);
		
		Response resp = target("auth/register").request()
				.post(Entity.entity(manager, MediaType.APPLICATION_JSON));

		verify(managerDaoMock).createManager(any(Manager.class));
		verify(managerDaoMock).findManagerByEmail(manager.getEmail());

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

}
