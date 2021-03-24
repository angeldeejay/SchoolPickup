package com.uniajc.schoolpickup.repositories;

import com.uniajc.schoolpickup.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {}
