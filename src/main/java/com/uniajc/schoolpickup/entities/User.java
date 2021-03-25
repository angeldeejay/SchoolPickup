package com.uniajc.schoolpickup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uniajc.schoolpickup.generics.GenericEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User extends GenericEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @Column(name = "first_name", nullable = false)
  String firstName;

  @Column(name = "last_name", nullable = false)
  String lastName;

  @Column(name = "identification", nullable = false)
  String identification;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  String password;

  @JsonManagedReference
  @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
  Parent parent;

  // Getters & Setters
  public Long getId() {
    return id;
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

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdentification() {
    return identification;
  }

  public void setIdentification(String identification) {
    this.identification = identification;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Parent getParent() {
    return parent;
  }

  public void setParent(Parent parent) {
    this.parent = parent;
  }

  @Transient
  public boolean hasParentRole() {
    return this.parent != null;
  }

  @Transient
  public boolean hasAdminRole() {
    return this.parent == null;
  }

  public String getFullName() {
    return firstName + ' ' + lastName;
  }
}
