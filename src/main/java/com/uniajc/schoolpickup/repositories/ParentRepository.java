package com.uniajc.schoolpickup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniajc.schoolpickup.entities.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
}