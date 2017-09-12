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

import com.vehicleman.backend.dao.UserDAO;
import com.vehicleman.backend.entities.User;

@Path("users")
public class UserService {
	
	UserDAO userDao = new UserDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<User> getUsers() {
		
		List<User> users = userDao.getUsers();
		
		return users;
	}
	
	@GET
	@Path("user/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User getUser(@PathParam("id") String id) {
		
		return userDao.getUser(id);
	}
	
	@POST	
	@Path("user")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User createUser(User user) {
		
		userDao.createUser(user);
		
		// return response code and message and maybe created entity
		return user;
	}
	
	@PUT	
	@Path("user/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User updateUser(@PathParam("id") String id, User user) {
		
		user.setId(id);
		userDao.updateUser(user);
		
		return user;
	}
	
	@DELETE
	@Path("user/{id}")
	public Response killUser(@PathParam("id") String id) {

		userDao.deleteUser(id);			
		
		return Response.noContent().build();
	}
}
