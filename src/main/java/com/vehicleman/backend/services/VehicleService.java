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

import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.Vehicle;

@Path("vehicles")
public class VehicleService {
	
	VehicleDAO vehicleDao = new VehicleDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Vehicle> getVehicles() {
		
		List<Vehicle> vehicles = vehicleDao.getVehicles();
		
		return vehicles;
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Vehicle getVehicle(@PathParam("id") String id) {
		
		return vehicleDao.getVehicle(id);
	}
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Vehicle createVehicle(Vehicle vehicle) {
		
		vehicleDao.createVehicle(vehicle);
		
		// return response code and message and maybe created entity
		return vehicle;
	}
	
	@PUT	
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Vehicle updateVehicle(@PathParam("id") String id, Vehicle vehicle) {
		
		vehicle.setId(id);
		vehicleDao.updateVehicle(vehicle);
		
		return vehicle;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteVehicle(@PathParam("id") String id) {

		vehicleDao.deleteVehicle(id);			
		
		return Response.noContent().build();
	}
}
