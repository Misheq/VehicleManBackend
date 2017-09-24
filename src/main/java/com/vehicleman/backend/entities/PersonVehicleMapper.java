package com.vehicleman.backend.entities;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonVehicleMapper implements Serializable {

	private static final long serialVersionUID = 1L;

	private Person person;
	private List<Vehicle> vehicles;

	public PersonVehicleMapper() {

	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

}
