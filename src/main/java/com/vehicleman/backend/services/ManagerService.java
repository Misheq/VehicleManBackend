package com.vehicleman.backend.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;

import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.entities.Manager;

import io.swagger.annotations.Api;

@Path("managers")
@Api(value = "Managers")
public class ManagerService {

	protected ManagerDAO managerDao = new ManagerDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getManagers() {

		List<Manager> managers = managerDao.getManagers();

		return Response.ok(managers.toArray(new Manager[managers.size()])).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getManager(@PathParam("id") int id) {

		Manager mgr = managerDao.getManager(id);

		if (mgr == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Manager with id: " + id + " not found\"}").build());
		}

		return Response.ok(mgr).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createManager(Manager manager) {

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

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateManager(@PathParam("id") int id, Manager manager) {

		// checks if manager with id exists
		Manager mgr = managerDao.getManager(id);

		if (mgr == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Manager with id: " + id + " not found\"}").build());
		}

		// TODO: check if required fields are given
		// TODO: check if valid fields are given
		// TODO: handle to update only fields that are updated
		manager.setManagerId(id);
		managerDao.updateManager(manager);

		return Response.ok().entity("{\"message\":\"Manager with id: " + id + " has been updated successfully\"}")
				.header(HttpHeaders.LOCATION, "http://localhost:8082/vehicleman/api/managers/" + manager.getManagerId())
				.build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteManager(@PathParam("id") int id) {

		// checks if manager with id exists
		Manager mgr = managerDao.getManager(id);

		if (mgr == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Manager with id: " + id + " not found\"}").build());
		}

		managerDao.deleteManager(id);

		return Response.noContent().entity("{\"message\":\"Manager with id: " + id + " deleted successfully\"}")
				.build();
	}

	// helper
	private boolean managerAlreadyExists(String email) {
		return managerDao.findManagerByEmail(email) != null;
	}
}
