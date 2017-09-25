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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.entities.Manager;

@Path("managers")
public class ManagerService {

	ManagerDAO managerDao = new ManagerDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getManagers() {

		List<Manager> managers = managerDao.getManagers();

		return Response.ok(managers).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getManager(@PathParam("id") int id) {

		Manager mgr = managerDao.getManager(id);

		if(mgr == null) {
			throw new NotFoundException();
		}

		return Response.ok(mgr).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createManager(Manager manager) {

		managerDao.createManager(manager);

		return Response.status(201).entity("Manager has been created successfully").build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateManager(@PathParam("id") int id, Manager manager) {

		// checks if manager with id exists
		Manager mgr = managerDao.getManager(id);

		if(mgr == null) {
			throw new NotFoundException();
		}

		// TODO: check if required fields are given
		// TODO: check if valid fields are given
		// TODO: handle to update only fields that are updated
		manager.setManagerId(id);
		managerDao.updateManager(manager);

		return Response.ok().entity("Manager with id: " + id + " has been updated successfully").build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteManager(@PathParam("id") int id) {

		// checks if manager with id exists
		Manager mgr = managerDao.getManager(id);

		if(mgr == null) {
			throw new NotFoundException();
		}

		managerDao.deleteManager(id);

		return Response.noContent().entity("Manager with id: " + id + "deleted successfully").build();
	}
}
