package com.example.das_system_app.rest.valueobject;

import java.io.Serializable;

public class Vorlesung implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8718799384980221553L;
	private Integer vid;

	private String name;
	private String inhalt;
	private String anmeldecode;
	private User dozent;

	public Vorlesung() {

	}

	public User getDozent() {
		return dozent;
	}

	public void setDozent(User dozent) {
		this.dozent = dozent;
	}
	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public String getAnmeldecode() {
		return anmeldecode;
	}

	public void setAnmeldecode(String anmeldecode) {
		this.anmeldecode = anmeldecode;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
