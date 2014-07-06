package com.example.das_system_app.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * wraps Arraylist of Groups to pass through Activites
 * 
 * @author marcman
 * 
 */
public class DataWrapper<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<T> list;

	public DataWrapper(List<T> glist) {
		this.list = (ArrayList<T>) glist;
	}

	public ArrayList<T> getList() {
		return this.list;
	}

}