package com.uniajc.schoolpickup.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uniajc.schoolpickup.generics.GenericEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "parents")
public class Parent extends GenericEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;

	@Column(name = "first_name", nullable = false)
	String firstName;

	@Column(name = "last_name", nullable = false)
	String lastName;

	@Column(name = "identification_type", nullable = false)
	String identificationType;

	@Column(name = "identification_value", nullable = false)
	String identificationValue;

	@Column(name = "email", nullable = false)
	String email;

	@Column(name = "password", nullable = false)
	String password;

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationValue() {
		return identificationValue;
	}

	public void setIdentificationValue(String identificationValue) {
		this.identificationValue = identificationValue;
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
}