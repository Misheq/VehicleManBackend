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
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.utils.ApiConstants;

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

		Manager manager = checkIfManagerExists(id);

		return Response.ok(manager).build();
	}

	@GET
	@Path("/{id}/persons")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getManagerPersons(@PathParam("id") int id) {

		Manager manager = checkIfManagerExists(id);

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

	@GET
	@Path("/{id}/vehicles")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getManagerVehicles(@PathParam("id") int id) {

		Manager manager = checkIfManagerExists(id);

		String email = securityContext.getUserPrincipal().getName();

		if (!manager.getEmail().equals(email)) {
			return Response.status(Status.FORBIDDEN).entity("{\"message\":\"Access forbidden\"}").build();
		}

		List<Vehicle> vehicles = managerDao.getManagerVehicles(id);
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).build();

		try {
			response = Response.ok().entity(new ObjectMapper().writeValueAsString(vehicles)).build();
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
		// TODO: check if email exist
		// TODO: handle password update
		Manager oldManager = checkIfManagerExists(id);

		manager.setManagerId(id);
		manager.setPassword(oldManager.getPassword());
		managerDao.updateManager(manager);

		return Response.ok().entity("{\"message\":\"Manager with id: " + id + " has been updated successfully\"}")
				.header(HttpHeaders.LOCATION, ApiConstants.BASE_URL + "managers/" + manager.getManagerId()).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteManager(@PathParam("id") int id) {

		// checks if manager with id exists
		checkIfManagerExists(id);

		managerDao.deleteManager(id);

		return Response.noContent().entity("{\"message\":\"Manager with id: " + id + " deleted successfully\"}")
				.build();
	}

	/////////// helpers /////////////////

	private Manager checkIfManagerExists(int id) {

		Manager manager = managerDao.getManager(id);

		if (manager == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Manager with id: " + id + " not found\"}").build());
		}

		return manager;
	}

}
