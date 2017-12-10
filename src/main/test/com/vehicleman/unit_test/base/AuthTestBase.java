package com.vehicleman.unit_test.base;

import static org.mockito.Mockito.mock;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.services.AuthService;

class AuthServiceMock extends AuthService {

	public AuthServiceMock() {
		super.managerDao = mock(ManagerDAO.class);
	}

	protected ManagerDAO getManagerDaoMock() {
		return managerDao;
	}
}

public class AuthTestBase extends JerseyTest {

	protected ManagerDAO managerDaoMock;

	@Override
	protected Application configure() {
		AuthServiceMock authServiceMock = new AuthServiceMock();
		managerDaoMock = authServiceMock.getManagerDaoMock();
		return new ResourceConfig().register(authServiceMock);
	}
}
