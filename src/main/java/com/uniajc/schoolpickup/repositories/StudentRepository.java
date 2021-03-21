package com.uniajc.schoolpickup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniajc.schoolpickup.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
