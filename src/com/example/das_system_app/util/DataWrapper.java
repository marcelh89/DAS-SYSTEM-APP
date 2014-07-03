package com.example.das_system_app.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.das_system_app.model.Gruppe;

/**
 * wraps Arraylist of Groups to pass through Activites
 * 
 * @author marcman
 * 
 */
public class DataWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Gruppe> groups;

	public DataWrapper(List<Gruppe> grouplist) {
		this.groups = (ArrayList<Gruppe>) grouplist;
	}

	public ArrayList<Gruppe> getGroups() {
		return this.groups;
	}

}