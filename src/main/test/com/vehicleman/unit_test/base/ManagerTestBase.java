package com.vehicleman.unit_test.base;

import static org.mockito.Mockito.mock;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.services.ManagerService;

class ManagerServiceMock extends ManagerService {

	public ManagerServiceMock() {
		super.managerDao = mock(ManagerDAO.class);
	}

	protected ManagerDAO getManagerDaoMock() {
		return managerDao;
	}

}

public class ManagerTestBase extends JerseyTest {

	public static final String ENDPOINT = "managers/";
	protected ManagerDAO managerDaoMock;

	@Override
	protected Application configure() {
		ManagerServiceMock managerServiceMock = new ManagerServiceMock();
		managerDaoMock = managerServiceMock.getManagerDaoMock();
		return new ResourceConfig().register(managerServiceMock);
	}
}
