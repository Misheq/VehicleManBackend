package com.vehicleman.backend.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.entities.Manager;

@Path("auth")
public class AuthService {

	protected ManagerDAO managerDao = new ManagerDAO();

	@GET
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response login() {

		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Path("/register")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response register(Manager manager) {
		if (managerAlreadyExists(manager.getEmail())) {
			throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
					.entity("{\"error\":\"Manager with the email " + manager.getEmail() + " already exists\"}")
					.build());
		}

		manager.setPassword(BCrypt.hashpw(manager.getPassword(), BCrypt.gensalt()));
		managerDao.createManager(manager);

		return Response.status(201).entity("{\"message\":\"Manager has been created successfully\"}")
				.header(HttpHeaders.LOCATION, "http://localhost:8082/vehicleman/api/managers/" + manager.getManagerId())
				.build();
	}

	// helper
	private boolean managerAlreadyExists(String email) {
		return managerDao.findManagerByEmail(email) != null;
	}

	private String authToken(Manager manager) {

		String email = manager.getEmail();
		String password = manager.getPassword();

		String token = email + ":" + password;
		String authToken = Base64.encodeAsString(token);

		return authToken;
	}
}
