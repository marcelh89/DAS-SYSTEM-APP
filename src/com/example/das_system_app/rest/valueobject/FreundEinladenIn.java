package com.example.das_system_app.rest.valueobject;

public class FreundEinladenIn {

	private Integer gruppenid;
	private Integer userid;

	public FreundEinladenIn(int gruppenid, int userid) {
		this.gruppenid = gruppenid;
		this.userid = userid;
	}

	public Integer getGruppenid() {
		return gruppenid;
	}

	public void setGruppenid(Integer gruppenid) {
		this.gruppenid = gruppenid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
