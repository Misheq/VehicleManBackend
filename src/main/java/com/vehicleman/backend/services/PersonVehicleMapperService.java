package com.vehicleman.backend.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vehicleman.backend.dao.PersonDAO;
import com.vehicleman.backend.dao.VehicleDAO;
import com.vehicleman.backend.entities.PersonVehicleMapper;
import com.vehicleman.backend.entities.Vehicle;

@Path("mapper")
public class PersonVehicleMapperService {

	VehicleDAO vhDAO = new VehicleDAO();
	PersonDAO pDAO = new PersonDAO();

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createVehicle(PersonVehicleMapper pvm) {

		// add existing person with id, add one or many existing vehicles with id
		createMissingEntityAndMapWithExisting(pvm);

		return Response.ok().entity("{\"message\":\"Person and vehicle assigned successfully\"}").build();
	}

	private void createMissingEntityAndMapWithExisting(PersonVehicleMapper pvm) {
		for (Vehicle v : pvm.getVehicles()) {
			v.setPerson(pvm.getPerson());
			vhDAO.updateVehicle(v);
		}
	}
}
