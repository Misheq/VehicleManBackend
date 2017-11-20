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
		@NamedQuery(name = "Vehicle.get_Vehicle_By_Registration_Number", query = "from Vehicle v where v.registrationNumber = :registrationNumber"),
		@NamedQuery(name = "Vehicle.get_All", query = "from Vehicle v"),
		@NamedQuery(name = "Vehicle.get_Vehicle_By_Id", query = "from Vehicle v where v.vehicleId = :id") })

@Entity
@Table(name = "VEHICLE")
@XmlRootElement
public class Vehicle implements Serializable {

	private static final long serialVersionUID = -5986117033315048956L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //(generator = "uuid")
	//	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "vehicle_id", unique = true, nullable = false)
	private int vehicleId;

	@Column(name = "vehicle_type", nullable = false)
	private String vehicleType;

	@Column(name = "reg_no", unique = true, nullable = false)
	private String registrationNumber;

	@Column(name = "manager_id", nullable = false)
	private int managerId;

	@Column(name = "color", nullable = true)
	private String color = "";

	@Column(name = "description", nullable = true)
	private String description = "";

	@Column(name = "servicing_date")
	private String servicingDate = "";

	@Column(name = "assignee_id")
	private String assigneeId = "";

	@ManyToOne(targetEntity = Person.class, //
			cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER) // CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
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

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServicingDate() {
		return servicingDate;
	}

	public void setServicingDate(String servicingDate) {
		this.servicingDate = servicingDate;
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
		result = prime * result + (registrationNumber == null ? 0 : registrationNumber.hashCode());
		result = prime * result + (vehicleType == null ? 0 : vehicleType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vehicle other = (Vehicle) obj;
		if (vehicleId == 0) {
			if (other.vehicleId != 0) {
				return false;
			}
		} else if (vehicleId != other.vehicleId) {
			return false;
		}
		if (registrationNumber == null) {
			if (other.registrationNumber != null) {
				return false;
			}
		} else if (!registrationNumber.equals(other.registrationNumber)) {
			return false;
		}
		if (vehicleType == null) {
			if (other.vehicleType != null) {
				return false;
			}
		} else if (!vehicleType.equals(other.vehicleType)) {
			return false;
		}
		return true;
	}
}
