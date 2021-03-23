package com.uniajc.schoolpickup.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import com.uniajc.schoolpickup.generics.GenericEntity;
import javax.persistence.CascadeType;

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

  @OneToOne(mappedBy = "parent")
  private User user;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Student> students;

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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }
}
