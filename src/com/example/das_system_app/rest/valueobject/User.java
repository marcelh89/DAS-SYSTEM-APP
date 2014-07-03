package com.example.das_system_app.rest.valueobject;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -282126619116500134L;
	private Integer uid;
	private String email;
	private String forename;
	private String surname;
	private String password;
	private Date birthDate;
	private boolean dozent;
	private String lastLocation;

	public User(int id, String forename, String surname, String email,
			String password, Date birthDate, boolean dozent) {
		this.email = email;
		this.password = password;
		this.forename = forename;
		this.surname = surname;
		this.setBirthDate(birthDate);
		this.setUid(id);
		this.setDozent(dozent);
	}

	public User(String forename, String surname, String email, String password,
			Date birthDate, boolean dozent) {
		this.email = email;
		this.password = password;
		this.forename = forename;
		this.surname = surname;
		this.setBirthDate(birthDate);
		this.setDozent(dozent);
	}

	// Login
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User() {
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isDozent() {
		return dozent;
	}

	public void setDozent(boolean dozent) {
		this.dozent = dozent;
	}

	public String getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(String lastLocation) {
		this.lastLocation = lastLocation;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", email=" + email + ", forename="
				+ forename + ", surname=" + surname + ", password=" + password
				+ ", birthDate=" + birthDate + ", dozent=" + dozent + "]";
	}

	public boolean equals(Object other) {
		if (other.getClass() == this.getClass()) {
			return this.getUid() == ((User) other).getUid();
		}
		return false;
	}

}
