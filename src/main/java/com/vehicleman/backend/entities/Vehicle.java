package com.vehicleman.backend.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({

		@NamedQuery(name = "Vehicle.get_All", query = "from Vehicle a"),
		@NamedQuery(name = "Vehicle.get_Vehicle_By_Id", query = "from Vehicle a where a.id = :id") })

@Entity
@Table(name = "VEHICLE")
@XmlRootElement
public class Vehicle implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", length = 45, unique = true, nullable = false)
	private String id;

	@Column(name = "vehicle_type", nullable = false)
	private String vehicleType;

	@Column(name = "reg_no", unique = true)
	private String registrationNumber;
	
//	@Column(name = "person_id", nullable = true)
//	private String personId;
//	
	@ManyToOne
	@JoinColumn(name = "person_id")
	Person person;

	// String brand;
	// String model;
	// String company;
	// Person assignedPerson;
	// Date dateOfLastCheck;
	// Date dateOfNextCheck;
	// Date dateOfAquirement;
	// Date registrationDate;
	// Date registrationExpirationDate;
	// int totalDistance;
	// int height;
	// int width;
	// int length;
	// int weight;
	// List<BufferedImage> pictures;

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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
		result = prime * result + ((vehicleType == null) ? 0 : vehicleType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (registrationNumber == null) {
			if (other.registrationNumber != null)
				return false;
		} else if (!registrationNumber.equals(other.registrationNumber))
			return false;
		if (vehicleType == null) {
			if (other.vehicleType != null)
				return false;
		} else if (!vehicleType.equals(other.vehicleType))
			return false;
		return true;
	}
}
