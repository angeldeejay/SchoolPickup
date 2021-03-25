package com.uniajc.schoolpickup.repositories;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  @Query("FROM Student s WHERE s.parent = ?1")
  public List<Student> findByParent(Parent parent);
}
