package com.example.das_system_app.rest.valueobject;

public class KursAnmeldenIn {
	private Integer userid;
	private String anmeldecode;
	private String datum;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getAnmeldecode() {
		return anmeldecode;
	}
	public void setAnmeldecode(String anmeldecode) {
		this.anmeldecode = anmeldecode;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	@Override
	public String toString() {
		return "KursAnmeldenIn [userid=" + userid + ", anmeldecode="
				+ anmeldecode + ", datum=" + datum + "]";
	}
	
	
	
	
}
