package com.uniajc.schoolpickup.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.services.StudentService;
import com.uniajc.schoolpickup.util.JsonUtil;
import com.uniajc.schoolpickup.util.Mocker;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.uniajc.schoolpickup.security.WithMockAuthorityUser;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    @WithMockAuthorityUser("admin")
    public void test_FindAllEntities_Admin_Success() throws Exception {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            students.add(Mocker.getStudent(Long.valueOf(i)));
        }

        // Service mock definition
        given(studentService.findAllEntities()).willReturn(students);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/students") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        String jsonResponse = result.getResponse().getContentAsString();
        List<Student> foundStudents = JsonUtil.toInstances(jsonResponse, new TypeReference<List<Student>>() {
        });

        assertTrue(students.containsAll(foundStudents));

        // Controller call should reach the service
        verify(studentService).findAllEntities();
    }

    @WithMockAuthorityUser("parent")
    public void test_FindAllEntities_Parent_Success() throws Exception {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            students.add(Mocker.getStudent(Long.valueOf(i)));
        }

        // Service mock definition
        given(studentService.findAllEntities()).willReturn(students);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/students") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        String jsonResponse = result.getResponse().getContentAsString();
        List<Student> foundStudents = JsonUtil.toInstances(jsonResponse, new TypeReference<List<Student>>() {
        });

        assertTrue(students.containsAll(foundStudents));

        // Controller call should reach the service
        verify(studentService).findAllEntities();
    }

    public void test_FindAllEntities_Anonymous_Failure() throws Exception {
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/students") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_GetById_Admin_Success() throws Exception {
        Student student = Mocker.getStudent(1L);
        Optional<Student> optionalStudent = Optional.of(student);

        // Service mock definition
        given(studentService.findEntityById(isA(Long.class))).willReturn(optionalStudent);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/students/" + student.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        Student foundStudent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Student.class);
        assertTrue(student.equals(foundStudent));

        // Controller call should reach the service
        verify(studentService).findEntityById(isA(Long.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_GetById_Parent_Success() throws Exception {
        Student student = Mocker.getStudent(1L);
        Optional<Student> optionalStudent = Optional.of(student);

        // Service mock definition
        given(studentService.findEntityById(isA(Long.class))).willReturn(optionalStudent);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/students/" + student.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        Student foundStudent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Student.class);
        assertTrue(student.equals(foundStudent));

        // Controller call should reach the service
        verify(studentService).findEntityById(isA(Long.class));
    }

    @Test
    public void test_GetById_Anonymous_Failure() throws Exception {
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                get("/students/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_Add_Admin_Success() throws Exception {
        Student student = Mocker.getStudent(1L);

        // Service mock definition
        given(studentService.saveEntity(isA(Student.class))).willReturn(student);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                post("/students") //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(student))) //
                .andExpect(status().isCreated()) //
                .andReturn();

        // Controller response assertions
        Student createdStudent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Student.class);
        assertTrue(student.equals(createdStudent));

        // Controller call should reach the service
        verify(studentService).saveEntity(isA(Student.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_Add_Parent_Success() throws Exception {
        Student student = Mocker.getStudent(1L);

        // Service mock definition
        given(studentService.saveEntity(isA(Student.class))).willReturn(student);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                post("/students") //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(student))) //
                .andExpect(status().isCreated()) //
                .andReturn();

        // Controller response assertions
        Student createdStudent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Student.class);
        assertTrue(student.equals(createdStudent));

        // Controller call should reach the service
        verify(studentService).saveEntity(isA(Student.class));
    }

    @Test
    public void test_Add_Anonymous_Failure() throws Exception {
        Student student = Mocker.getStudent(1L);
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                post("/students") //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(student))) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_Delete_Admin_Success() throws Exception {
        // Service mock definition
        doNothing().when(studentService).deleteEntity(isA(Long.class));

        mockMvc.perform( //
                delete("/students/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isNoContent()) //
                .andReturn();

        // Controller call should reach the service
        verify(studentService).deleteEntity(isA(Long.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_Delete_Parent_Success() throws Exception {
        // Service mock definition
        doNothing().when(studentService).deleteEntity(isA(Long.class));

        mockMvc.perform( //
                delete("/students/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isNoContent()) //
                .andReturn();

        // Controller call should reach the service
        verify(studentService).deleteEntity(isA(Long.class));
    }

    @Test
    public void test_Delete_Anonymous_Failure() throws Exception {
        mockMvc.perform( //
                delete("/students/1") //
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }

    @Test
    @WithMockAuthorityUser("admin")
    public void test_Update_Admin_Success() throws Exception {
        Student student = Mocker.getStudent(1L);
        Optional<Student> optionalStudent = Optional.of(student);

        // Service mock definition
        given(studentService.updateEntity(isA(Long.class), isA(Student.class))).willReturn(optionalStudent);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                put("/students/" + student.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(student))) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        Student updatedStudent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Student.class);
        assertTrue(student.equals(updatedStudent));

        // Controller call should reach the service
        verify(studentService).updateEntity(isA(Long.class), isA(Student.class));
    }

    @Test
    @WithMockAuthorityUser("parent")
    public void test_Update_Parent_Success() throws Exception {
        Student student = Mocker.getStudent(1L);
        Optional<Student> optionalStudent = Optional.of(student);

        // Service mock definition
        given(studentService.updateEntity(isA(Long.class), isA(Student.class))).willReturn(optionalStudent);

        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                put("/students/" + student.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(student))) //
                .andExpect(status().isOk()) //
                .andReturn();

        // Controller response assertions
        Student updatedStudent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Student.class);
        assertTrue(student.equals(updatedStudent));

        // Controller call should reach the service
        verify(studentService).updateEntity(isA(Long.class), isA(Student.class));
    }

    @Test
    public void test_Update_Anonymous_Failure() throws Exception {
        Student student = Mocker.getStudent(1L);
        // Controller request assertions
        MvcResult result = mockMvc.perform( //
                put("/students/" + student.getId().toString()) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .content(JsonUtil.toJson(student))) //
                .andExpect(status().isUnauthorized()) //
                .andReturn();
    }
}
