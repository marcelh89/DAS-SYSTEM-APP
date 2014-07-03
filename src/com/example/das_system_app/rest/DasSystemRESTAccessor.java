package com.example.das_system_app.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import android.util.Log;

import com.example.das_system_app.rest.valueobject.KursAnmeldenIn;
import com.example.das_system_app.rest.valueobject.RauminfoIn;
import com.example.das_system_app.rest.valueobject.Rauminformation;
import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.rest.valueobject.User_old;

public class DasSystemRESTAccessor implements IDasSystemRESTAccessor {

	protected static String logger = DasSystemRESTAccessor.class
			.getSimpleName();
	private IDasSystemRESTAccessor restClient;

	private static String URLS[] = { "http://10.0.2.2:8080/DAS-SYSTEM-SERVER",
			"http://192.168.178.60:8080/DAS-SYSTEM-SERVER",
			"http://192.168.178.46:8080/DAS-SYSTEM-SERVER"};

	public DasSystemRESTAccessor() {
		this.restClient = ProxyFactory.create(IDasSystemRESTAccessor.class,
				URLS[2], new ApacheHttpClient4Executor());
		Log.i(logger, "initialised restClient: " + restClient);
	}

	@Override
	public User_old login(User_old user) {
		Log.i(logger, "versuche login " + user.getEmail() + user.getPassword());
		return restClient.login(user);
	}

	@Override
	public boolean register(User user) {
		boolean retVal = false;
		try{
			retVal = restClient.register(user);
		}catch(Exception e){
			e.printStackTrace();
			retVal = false;
		}
		return retVal;
	}

	@Override
	public User_old halloWelt() {
		return restClient.halloWelt();
	}

	@Override
	@POST
	@Path("/login2")
	public User login2(User user) {
		Log.i(logger, "versuche login " + user.getEmail() + user.getPassword());
		User retUser = null;
		try{
			retUser = restClient.login2(user);
		}catch(Exception e){
			e.printStackTrace();
			retUser = null;
		}
		return retUser;
	}

	@Override
	@POST
	@Path("/rauminfo/")
	public Rauminformation getRauminformation(RauminfoIn rIn) {
		Log.i(logger, "Hole Rauminfo " + rIn.getRaumNr());
		Rauminformation rauminfo = null;
		try{
			rauminfo = restClient.getRauminformation(rIn);	
		}catch(Exception e){
			e.printStackTrace();
			rauminfo = null;
		}
		return rauminfo;
	}

	@Override
	@POST
	@Path("/vorlesung/teilnehmer/anmelden")
	public Rauminformation anKursAnmelden(KursAnmeldenIn kIn) {
		Log.i(logger, "Melde an Kurs an " + kIn);
		Rauminformation rauminfo = null;
		try{
			rauminfo = restClient.anKursAnmelden(kIn);	
		}catch(Exception e){
			e.printStackTrace();
			rauminfo = null;
		}
		return rauminfo;
	}
}
