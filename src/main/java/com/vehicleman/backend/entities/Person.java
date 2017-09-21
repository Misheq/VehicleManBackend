package com.vehicleman.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({

		@NamedQuery(name = "Person.get_All", query = "from Person a"), 
		@NamedQuery(name = "Person.get_Person_By_Id", query = "from Person a where a.id = :id") 	
})

@Entity
@Table(name = "person")
@XmlRootElement
public class Person implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", length = 45, unique = true, nullable = false)
	String id;

	@Column(name = "first_name", nullable = false)
	String firstName;

	@Column(name = "last_name", nullable = false)
	String lastName;

	@Column(name = "email", nullable = false)
	String email;

	@Column(name = "company_name", nullable = true)
	String companyName;

	@Column(name = "phone", nullable = true)
	String phone;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	List<Vehicle> vehicles = new ArrayList<>();

	public Person() {

		companyName = "";
		phone = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Vehicle> getAssignedVehicle() {
		return vehicles;
	}

	public void setAssignedVehicle(List<Vehicle> assignedVehicles) {
		this.vehicles = assignedVehicles;
	}
	
	public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setPerson(this);
    }
 
    public void removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setPerson(null);
    }
}
