package com.uniajc.schoolpickup.repositories;

import com.uniajc.schoolpickup.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {}
