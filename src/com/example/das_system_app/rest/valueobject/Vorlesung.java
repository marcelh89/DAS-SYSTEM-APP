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

	public Vorlesung() {

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
		return "Vorlesung [vid=" + vid + ", name=" + name + ", inhalt="
				+ inhalt + ", anmeldecode=" + anmeldecode + "]";
	}
	
	
}
