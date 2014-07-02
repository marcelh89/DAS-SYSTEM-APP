package com.example.das_system_app.rest.valueobject;

public class Rauminformation {
	private String raumNr;
	private String begin;
	private String ende;
//	private Vorlesung vorlesung;
	private Integer vid;

	private String name;
	private String inhalt;
	private String anmeldecode;
	
	public String getRaumNr() {
		return raumNr;
	}
	public void setRaumNr(String raumNr) {
		this.raumNr = raumNr;
	}

	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnde() {
		return ende;
	}
	public void setEnde(String ende) {
		this.ende = ende;
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
		return "Rauminformation [raumNr=" + raumNr + ", begin=" + begin
				+ ", ende=" + ende + ", vid=" + vid + ", name=" + name
				+ ", inhalt=" + inhalt + ", anmeldecode=" + anmeldecode + "]";
	}
	
	
}
