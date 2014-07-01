package com.example.das_system_app.rest.valueobject;

import java.io.Serializable;
import java.util.Date;

public class User_old implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5427760379791471498L;
	private int id;
	private String forename, surname, email, password;
	private Date birthDate;
	private boolean dozent;

	// registration
	public User_old(int id, String forename, String surname, String email, String password, Date birthDate, boolean dozent) {
		this.email = email;
		this.password = password;
		this.forename = forename;
		this.surname = surname;
		this.setBirthDate(birthDate);
		this.setId(id);
		this.setDozent(dozent);
	}
	
	public User_old( String forename, String surname, String email, String password, Date birthDate, boolean dozent) {
		this.email = email;
		this.password = password;
		this.forename = forename;
		this.surname = surname;
		this.setBirthDate(birthDate);
		this.setDozent(dozent);
	}

	// Login
	public User_old(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User_old() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public boolean isDozent() {
		return dozent;
	}

	public void setDozent(boolean dozent) {
		this.dozent = dozent;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", forename=" + forename + ", surname="
				+ surname + ", email=" + email + ", password=" + password
				+ ", birthDate=" + birthDate + ", dozent=" + dozent + "]";
	}
	
	public boolean equals(User_old u){
		if(u.getId()==this.id){
			return true;
		}else{
			return false;
		}
	}
	
}
