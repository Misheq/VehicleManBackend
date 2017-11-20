package com.vehicleman.backend.entities;

import java.io.Serializable;
import java.security.Principal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({

		@NamedQuery(name = "Manager.get_All", query = "from Manager m"),
		@NamedQuery(name = "Manager.get_Manager_By_Id", query = "from Manager m where m.managerId = :id"),
		@NamedQuery(name = "Manager.get_Manager_By_Email", query = "from Manager m where m.email = :email"),
		@NamedQuery(name = "Manager.get_Manager_Persons", query = "from Person p where p.managerId = :id"),
		@NamedQuery(name = "Manager.get_Manager_Vehicles", query = "from Vehicle v where v.managerId = :id")

})

@Entity
@Table(name = "MANAGER")
@XmlRootElement
public class Manager implements Serializable, Principal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //(generator = "uuid")
	//	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "manager_id", unique = true, nullable = false)
	private int managerId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "phone", nullable = true)
	private String phone = "";

	@Column(name = "company_name", nullable = true)
	private String companyName = "";

	public Manager() {

	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Manager [id=" + managerId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ "]";
	}

	@Override
	public String getName() {
		return email;
	}

}
