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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.Person;
import com.vehicleman.backend.entities.Vehicle;
import com.vehicleman.backend.utils.ApiConstants;

import io.swagger.annotations.Api;

@Path("vehicles")
@Api(value = "Vehicles")
public class VehicleService {

	protected VehicleDAO vehicleDao = new VehicleDAO();
	protected PersonDAO personDao = new PersonDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVehicles() {

		List<Vehicle> vehicles = vehicleDao.getVehicles();

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(vehicles)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVehicle(@PathParam("id") int id) {

		validateVehicleExists(id);
		Vehicle vehicle = vehicleDao.getVehicle(id);

		ObjectMapper om = new ObjectMapper();

		try {
			return Response.ok().entity(om.writeValueAsString(vehicle)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return Response.ok().entity(vehicle).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createVehicleForPerson(Vehicle vehicle) {

		if (vehicle == null) {
			throw new NotFoundException(Response.status(404).entity("{\"error\":\"Vehicle is null\"}").build()); // should be bad request
		}

		registrationAlreadyExist(vehicle);

		vehicleDao.createVehicle(vehicle);

		return Response.status(Response.Status.CREATED).entity("{\"message\":\"Vehicle successfully created\"}")
				.header(HttpHeaders.LOCATION, ApiConstants.BASE_URL + "vehicles/" + vehicle.getVehicleId()).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateVehicle(@PathParam("id") int id, Vehicle vehicle) {

		validateVehicleExists(id);

		if (registrationChanged(vehicle)) {
			registrationAlreadyExist(vehicle);
		}

		vehicle.setVehicleId(id);

		Person person;

		/**
		 * if assignee id is not empty -> case 1: person exists -> case 2: person id is invalid
		 *
		 * if assignee id is empty -> set person to null
		 */
		if (hasPersonAssigned(vehicle)) {
			int personId = Integer.parseInt(vehicle.getAssigneeId());
			person = personDao.getPerson(personId);
			vehicle.setPerson(person);

			if (person == null) {
				vehicle.setAssigneeId("");
			}
		} else {
			person = null;
		}

		vehicleDao.updateVehicle(vehicle);

		return Response.ok().entity("{\"message\":\"Vehicle with id: " + id + " updated successfully\"}")
				.header(HttpHeaders.LOCATION, ApiConstants.BASE_URL + "vehicles/" + id).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteVehicle(@PathParam("id") int id) {

		validateVehicleExists(id);

		vehicleDao.deleteVehicle(id);

		// does not print out response :/
		return Response.status(204).entity("{\"message\":\"Vehicle with id: " + id + " deleted successfully\"}")
				.build();
	}

	/////////////// HELPERS ///////////////////////////

	private void validateVehicleExists(int id) {
		Vehicle vehicle = vehicleDao.getVehicle(id);
		if (vehicle == null) {
			throw new NotFoundException(
					Response.status(404).entity("{\"error\":\"Vehicle with id: " + id + " not found\"}").build());
		}
	}

	private void registrationAlreadyExist(Vehicle vehicle) {
		if (vehicleAlreadyExist(vehicle.getRegistrationNumber())) {
			throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
					.entity("{\"error\":\"Vehicle with the registration number " + vehicle.getRegistrationNumber()
							+ " already exists\"}")
					.build());
		}
	}

	private boolean registrationChanged(Vehicle vehicle) {
		Vehicle oldVehicle = vehicleDao.getVehicle(vehicle.getVehicleId());
		return !oldVehicle.getRegistrationNumber().equals(vehicle.getRegistrationNumber());
	}

	private boolean vehicleAlreadyExist(String registrationNumber) {
		return vehicleDao.getVehicleByRegistrationNumber(registrationNumber) != null;
	}

	private boolean hasPersonAssigned(Vehicle v) {
		return !v.getAssigneeId().equals("");
	}

}
