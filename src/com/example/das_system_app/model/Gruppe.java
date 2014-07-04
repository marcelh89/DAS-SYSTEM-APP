package com.example.das_system_app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.das_system_app.rest.valueobject.User;

public class Gruppe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private boolean isPublic;
	User creator; // one of the users
	List<User> users;

	public Gruppe(String name, boolean isPublic, User creator) {
		this.name = name;
		this.isPublic = isPublic;
		this.creator = creator;
		this.users = new ArrayList<User>();
		users.add(creator);
	}

	Gruppe() {

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

	@Override
	public String toString() {
		return name;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
