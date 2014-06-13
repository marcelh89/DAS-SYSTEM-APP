package com.example.das_system_app.model;

public class Gruppe {

	private String name;
	private boolean isPublic;

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
