package com.vehicleman.backend.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Manager> getManagers() {
		
		List<Manager> managers = managerDao.getManagers();
		
		return managers;
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Manager getManager(@PathParam("id") String id) {
		
		return managerDao.getManager(id);
	}
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Manager createManager(Manager manager) {
		
		managerDao.createManager(manager);
		
		// return response code and message and maybe created entity
		return manager;
	}
	
	@PUT	
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Manager updateManager(@PathParam("id") String id, Manager manager) {
		
		manager.setId(id);
		managerDao.updateManager(manager);
		
		return manager;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteManager(@PathParam("id") String id) {

		managerDao.deleteManager(id);			
		
		return Response.noContent().build();
	}
}
