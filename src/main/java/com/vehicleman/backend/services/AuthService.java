package com.vehicleman.backend.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.entities.Manager;

@Path("auth")
public class AuthService {

	protected ManagerDAO managerDao = new ManagerDAO();

	@GET
	@Path("/login/{email}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response login(@PathParam("email") String email) {

		// this manager can not be null
		Manager manager = managerDao.findManagerByEmail(email);

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(manager)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return Response.status(Status.BAD_REQUEST).build();
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
		manager = managerDao.createManager(manager);

		return Response.status(201).entity(manager)
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
