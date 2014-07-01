package com.example.das_system_app.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.rest.valueobject.User_old;


@Path("/dassystem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IDasSystemRESTAccessor {
	
	@GET
	@Path("/hi")
	@Produces(MediaType.APPLICATION_JSON)
	public User_old halloWelt();
	@POST
	@Path("/login")
	public User_old login(User_old user);
	@POST
	@Path("/login2")
	public User login2(User user);
	@POST
	@Path("/register")
	public boolean register(User user);
}
