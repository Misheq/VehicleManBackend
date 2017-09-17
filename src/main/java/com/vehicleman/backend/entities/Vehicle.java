package com.vehicleman.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({ 
	
	@NamedQuery(name = "Vehicle.get_All",	query = "from Vehicle a") ,
	@NamedQuery(name = "Vehicle.get_Vehicle_By_Id",	query = "from Vehicle a where a.id = :id") 		
})

@Entity
@Table(name = "vehicle")
@XmlRootElement
public class Vehicle {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	String id;
	
	@Column(name = "vehicle_type", nullable = false)
	String vehicleType;
	
	@Column(name = "reg_no", unique = true)
	String registrationNumber;
	
//	@ManyToOne
//	@JoinColumn(name = "person_id")
	@Column(name = "person_id")
	String personId;

	
//	String brand;
//	String model;
//	String company;
//	Person assignedPerson;
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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
//	public Person getAssignedPerson() {
//		return assignedPerson;
//	}
//
//	public void setAssignedPerson(Person assignedPerson) {
//		this.assignedPerson = assignedPerson;
//	}
}
