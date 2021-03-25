package com.uniajc.schoolpickup.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uniajc.schoolpickup.generics.GenericEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "pickup_requests")
public class PickupRequest extends GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @Column(name = "slot", nullable = false)
  Integer slot;

  @JsonManagedReference
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  Parent parent;

  @JsonManagedReference
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "student_id", referencedColumnName = "id")
  Student student;

  @Column(name = "picked_up_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pickedUpAt;

  // Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getSlot() {
    return slot;
  }

  public void setSlot(Integer slot) {
    this.slot = slot;
  }

  public Parent getParent() {
    return parent;
  }

  public void setParent(Parent parent) {
    this.parent = parent;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Date getPickedUpAt() {
    return pickedUpAt;
  }

  public void setPickedUpAt(Date pickedUpAt) {
    this.pickedUpAt = pickedUpAt;
  }
}
