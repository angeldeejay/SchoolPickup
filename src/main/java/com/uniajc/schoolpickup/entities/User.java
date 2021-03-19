package com.uniajc.schoolpickup.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import com.uniajc.schoolpickup.generics.GenericEntity;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    Parent parent;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
