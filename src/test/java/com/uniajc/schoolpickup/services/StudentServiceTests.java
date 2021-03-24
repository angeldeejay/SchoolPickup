package com.uniajc.schoolpickup.services;

import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.repositories.StudentRepository;
import com.uniajc.schoolpickup.util.Mocker;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTests {

  @Mock private StudentRepository repository;

  @InjectMocks private StudentService service;

  @Test
  public void test_FindAllEntities_Success() throws Exception {
    List<Student> students = new ArrayList<Student>();
    for (int i = 0; i < 5; i++) {
      students.add(Mocker.getStudent(Long.valueOf(i)));
    }

    when(repository.findAll()).thenReturn(students);

    List<Student> foundStudents = service.findAllEntities();

    assertTrue(students.containsAll(foundStudents));

    verify(repository).findAll();
  }

  @Test
  public void test_FindEntityById_Success() throws Exception {
    Student student = Mocker.getStudent(1L);
    Optional<Student> optionalStudent = Optional.of(student);

    when(repository.findById(isA(Long.class))).thenReturn(optionalStudent);

    Optional<Student> foundStudent = service.findEntityById(1L);
    assertTrue(student.equals(foundStudent.get()));

    verify(repository).findById(isA(Long.class));
  }

  @Test
  public void test_SaveEntity_Success() throws Exception {
    Student student = Mocker.getStudent(1L);

    when(repository.save(any(Student.class))).thenReturn(student);

    Student created = service.saveEntity(student);

    assertThat(created.equals(student));
    verify(repository).save(student);
  }

  @Test
  public void test_DeleteEntity_Success() throws Exception {
    Student student = Mocker.getStudent(1L);
    Optional<Student> optionalStudent = Optional.of(student);

    when(repository.findById(isA(Long.class))).thenReturn(optionalStudent);
    doNothing().when(repository).deleteById(isA(Long.class));

    service.deleteEntity(1L);

    verify(repository).findById(isA(Long.class));
    verify(repository).deleteById(isA(Long.class));
  }

  @Test
  public void test_UpdateEntity_Success() throws Exception {
    Student student = Mocker.getStudent(1L);
    Optional<Student> optionalStudent = Optional.of(student);

    when(repository.findById(isA(Long.class))).thenReturn(optionalStudent);
    when(repository.save(isA(Student.class))).thenReturn(student);

    Optional<Student> updatedStudent = service.updateEntity(student.getId(), student);
    assertTrue(student.equals(updatedStudent.get()));

    verify(repository).findById(isA(Long.class));
    verify(repository).save(isA(Student.class));
  }
}
