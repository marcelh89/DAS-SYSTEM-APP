package com.example.das_system_app.model;

import java.util.List;

import com.example.das_system_app.rest.valueobject.User;

public class Gruppe {

	private String name;
	private boolean isPublic;
	User creator; //one of the users
	List <User> users;

	public Gruppe(String name, boolean isPublic) {
		this.name = name;
		this.isPublic = isPublic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

}
