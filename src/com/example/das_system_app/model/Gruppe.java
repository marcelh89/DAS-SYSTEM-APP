package com.example.das_system_app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.das_system_app.rest.valueobject.User;

public class Gruppe implements Serializable {

	/**
	 * some static id assignment
	 */
	private static int ID = 0;

	private static final long serialVersionUID = 8740865935045063839L;
	private Integer gid;
	private String name;
	private boolean isPublic;
	User creator; // one of the users

	List<User> users;

	public Gruppe(Integer gid, String name, boolean isPublic, User creator) {
		this.setGid(gid == -1 ? ID++ : gid);
		this.name = name;
		this.isPublic = isPublic;
		this.creator = creator;
		this.users = new ArrayList<User>();
		users.add(creator);
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Gruppe() {

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

	@Override
	public int hashCode() {
		return this.gid;
	}

	@Override
	public boolean equals(Object object) {
		boolean isEqual = false;

		if (object != null && object instanceof Gruppe) {
			isEqual = (this.gid == ((Gruppe) object).gid);
		}

		return isEqual;
	}

}
