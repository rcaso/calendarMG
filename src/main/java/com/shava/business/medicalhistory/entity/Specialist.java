package com.shava.business.medicalhistory.entity;

import java.io.Serializable;

public class Specialist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5633753688954901150L;
	
	private String lastName;
	
	private String name;
	
	private String type;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
