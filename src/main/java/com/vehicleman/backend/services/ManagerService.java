package com.vehicleman.backend.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.entities.Manager;
import com.vehicleman.backend.entities.Person;

import io.swagger.annotations.Api;

@Path("managers")
@Api(value = "Managers")
public class ManagerService {

	protected ManagerDAO managerDao = new ManagerDAO();

	@Context
	private SecurityContext securityContext;

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

	@GET
	@Path("/{id}/persons")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getManagerPersons(@PathParam("id") int id) {

		Manager manager = managerDao.getManager(id);

		if (manager == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity("{\"message\":\"Manager with id " + id + " does not exist\"}").build();
		}

		String email = securityContext.getUserPrincipal().getName();

		if (!manager.getEmail().equals(email)) {
			return Response.status(Status.FORBIDDEN).entity("{\"message\":\"Access forbidden\"}").build();
		}

		List<Person> persons = managerDao.getManagerPersons(id);
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).build();

		try {
			response = Response.ok().entity(new ObjectMapper().writeValueAsString(persons)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return response;
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

}
