package com.vehicleman.backend.entities;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.vehicleman.backend.entities.Person;

@Entity
@Table(name = "vehicle")
@XmlRootElement
public class Vehicle {
	
	@Id
	String id;
	String vehicleType;
//	String brand;
//	String model;
	String registrationNumber;
//	String company;
	Person assignedPerson;
//	Date dateOfLastCheck;
//	Date dateOfNextCheck;
//	Date dateOfAquirement;
//	Date registrationDate;
//	Date registrationExpirationDate;
//	int totalDistance;
//	int height;
//	int width;
//	int length;
//	int weight;
//	List<BufferedImage> pictures;

	public Vehicle() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Person getAssignedPerson() {
		return assignedPerson;
	}

	public void setAssignedPerson(Person assignedPerson) {
		this.assignedPerson = assignedPerson;
	}
}
