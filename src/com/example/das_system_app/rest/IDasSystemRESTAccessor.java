package com.example.das_system_app.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.das_system_app.model.Gruppe;
import com.example.das_system_app.rest.valueobject.KursAnmeldenIn;
import com.example.das_system_app.rest.valueobject.RauminfoIn;
import com.example.das_system_app.rest.valueobject.Rauminformation;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.rest.valueobject.User_old;
import com.example.das_system_app.rest.valueobject.Vorlesung;

@Path("/dassystem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IDasSystemRESTAccessor {

	@GET
	@Path("/hi")
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

	@POST
	@Path("/rauminfo/")
	public Rauminformation getRauminformation(RauminfoIn rIn);

	@POST
	@Path("/vorlesung/teilnehmer/anmelden")
	public Rauminformation anKursAnmelden(KursAnmeldenIn kIn);

	@GET
	@Path("/gruppe/all")
	public List<Gruppe> getGroups();

	@GET
	@Path("/vorlesung/{dozentid}")
	public List<Vorlesung> getVorlesungByDozent(
			@PathParam("dozentid") int dozentid);

	@POST
	@Path("/vorlesung/update")
	public Boolean updateVorlesungCode(Vorlesung vorlesung);

	@POST
	@Path("/gruppe/user")
	public List<Gruppe> getGroups(User user);

	@POST
	@Path("/gruppe/add")
	public boolean addGroup(Gruppe gruppe);

	@POST
	@Path("/gruppe/delete")
	public boolean deleteGroup(Gruppe actGroup);

	@POST
	@Path("/gruppe/update")
	public boolean updateGroup(Gruppe gruppe, User newUser);

	@GET
	@Path("/testuser")
	public List<User> getUser();

}
