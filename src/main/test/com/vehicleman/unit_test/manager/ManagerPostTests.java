package com.vehicleman.unit_test.manager;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.vehicleman.backend.entities.Manager;
import com.vehicleman.unit_test.base.ManagerTestBase;

public class ManagerPostTests extends ManagerTestBase {

	@Test
	public void createManagerService() {

		Manager manager = new Manager();
		//		manager.setManagerId(1);
		//		manager.setFirstName("managerFirstName");
		//		manager.setLastName("managerLastName");
		//		manager.setEmail("managerEmail@email.com");

		doNothing().when(managerDaoMock).createManager(manager);

		Response resp = target(ENDPOINT).request().post(Entity.entity(manager, MediaType.APPLICATION_JSON));

		// something is not working correctly if I pass manager as the argument
		//		verify(managerDaoMock).createManager(manager);
		verify(managerDaoMock).createManager(any(Manager.class));

		System.out.println(resp.readEntity(String.class));
		assertEquals(Response.Status.CREATED.getStatusCode(), resp.getStatus());
	}

}
