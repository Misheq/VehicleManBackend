package com.vehicleman.backend.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;

@NamedQueries({

		@NamedQuery(name = "Vehicle.get_All", query = "from Vehicle v"), @NamedQuery(name = "Vehicle.get_Vehicle_By_Id", query = "from Vehicle v where v.vehicleId = :id") })

@Entity
@Table(name = "VEHICLE")
@XmlRootElement
public class Vehicle implements Serializable {

	private static final long serialVersionUID = -5986117033315048956L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //(generator = "uuid")
	//	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "vehicle_id", length = 45, unique = true, nullable = false)
	private int vehicleId;

	@Column(name = "vehicle_type", nullable = false)
	private String vehicleType;

	@Column(name = "reg_no", unique = true)
	private String registrationNumber;

	//	@Transient
	//	private String assignedPerson;

	@ManyToOne(targetEntity = Person.class, cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name = "person_id")
	private Person person;

	public Vehicle() {

	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
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
		result = (prime * result) + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
		result = (prime * result) + ((vehicleType == null) ? 0 : vehicleType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Vehicle other = (Vehicle) obj;
		if(vehicleId == 0) {
			if(other.vehicleId != 0) {
				return false;
			}
		} else if(vehicleId != other.vehicleId) {
			return false;
		}
		if(registrationNumber == null) {
			if(other.registrationNumber != null) {
				return false;
			}
		} else if(!registrationNumber.equals(other.registrationNumber)) {
			return false;
		}
		if(vehicleType == null) {
			if(other.vehicleType != null) {
				return false;
			}
		} else if(!vehicleType.equals(other.vehicleType)) {
			return false;
		}
		return true;
	}

	// String brand;
	// String model;
	// String company;
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
}
