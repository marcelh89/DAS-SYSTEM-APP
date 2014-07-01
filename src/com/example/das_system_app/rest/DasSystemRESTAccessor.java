package com.example.das_system_app.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import android.util.Log;

import com.example.das_system_app.rest.valueobject.User;
import com.example.das_system_app.rest.valueobject.User_old;

public class DasSystemRESTAccessor implements IDasSystemRESTAccessor {

	protected static String logger = DasSystemRESTAccessor.class
			.getSimpleName();
	private IDasSystemRESTAccessor restClient;

	private static String URLS[] = { "http://10.0.2.2:8080/DAS-SYSTEM-SERVER",
			"http://192.168.178.60:8080/DAS-SYSTEM-SERVER" };

	public DasSystemRESTAccessor() {
		this.restClient = ProxyFactory.create(IDasSystemRESTAccessor.class,
				URLS[0], new ApacheHttpClient4Executor());
		Log.i(logger, "initialised restClient: " + restClient);
	}

	@Override
	public User_old login(User_old user) {
		Log.i(logger, "versuche login " + user.getEmail() + user.getPassword());
		return restClient.login(user);
	}

	@Override
	public boolean register(User user) {
		return restClient.register(user);
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
		return restClient.login2(user);
	}

}
