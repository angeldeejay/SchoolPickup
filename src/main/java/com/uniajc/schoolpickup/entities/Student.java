package com.uniajc.schoolpickup.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uniajc.schoolpickup.generics.GenericEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "students")
public class Student extends GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @Column(name = "first_name", nullable = false)
  String firstName;

  @Column(name = "last_name", nullable = false)
  String lastName;

  @JsonBackReference
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id", nullable = false)
  private Parent parent;

  @Column(name = "level", nullable = false)
  private String level;

  @JsonIgnore @Transient private boolean pending;
  @JsonIgnore @Transient private PickupRequest pendingPickupRequest;

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

  public Parent getParent() {
    return parent;
  }

  public void setParent(Parent parent) {
    this.parent = parent;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public boolean isPending() {
    return pending;
  }

  public PickupRequest getPendingPickupRequest() {
    return pendingPickupRequest;
  }

  public void setPendingPickupRequest(PickupRequest pendingPickupRequest) {
    this.pending = pendingPickupRequest != null;
    this.pendingPickupRequest = pendingPickupRequest;
  }
}
