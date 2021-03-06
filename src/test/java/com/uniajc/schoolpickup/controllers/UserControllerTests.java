package com.uniajc.schoolpickup.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.uniajc.schoolpickup.entities.User;
import com.uniajc.schoolpickup.security.WithMockAuthorityUser;
import com.uniajc.schoolpickup.services.UserService;
import com.uniajc.schoolpickup.util.JsonUtil;
import com.uniajc.schoolpickup.util.Mocker;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.Filter;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

  @MockBean private UserService userService;

  @Autowired private WebApplicationContext context;

  @Autowired private Filter springSecurityFilterChain;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_FindAllEntities_Admin_Success() throws Exception {
    List<User> users = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      users.add(Mocker.getUser(Long.valueOf(i)));
    }

    // Service mock definition
    given(userService.findAllEntities()).willReturn(users);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/users").contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andReturn();

    // Controller response assertions
    String jsonResponse = result.getResponse().getContentAsString();
    List<User> foundUsers = JsonUtil.toInstances(jsonResponse, new TypeReference<List<User>>() {});

    assertTrue(users.containsAll(foundUsers));

    // Controller call should reach the service
    verify(userService).findAllEntities();
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_FindAllEntities_Parent_Failure() throws Exception {
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/users").contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  public void test_FindAllEntities_Anonymous_Failure() throws Exception {
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/users").contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_GetById_Admin_Success() throws Exception {
    User user = Mocker.getUser(1L);
    Optional<User> optionalUser = Optional.of(user);

    // Service mock definition
    given(userService.findEntityById(isA(Long.class))).willReturn(optionalUser);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/users/" + user.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andReturn();

    // Controller response assertions
    User foundUser = JsonUtil.toInstance(result.getResponse().getContentAsString(), User.class);
    assertTrue(user.equals(foundUser));

    // Controller call should reach the service
    verify(userService).findEntityById(isA(Long.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_GetById_Parent_Failure() throws Exception {
    User user = Mocker.getUser(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/users/" + user.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  public void test_GetById_Anonymous_Failure() throws Exception {
    User user = Mocker.getUser(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/users/" + user.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_Add_Admin_Success() throws Exception {
    User user = Mocker.getUser(1L);

    // Service mock definition
    given(userService.saveEntity(isA(User.class))).willReturn(user);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                post("/users") //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(user))) //
            .andExpect(status().isCreated()) //
            .andReturn();

    // Controller response assertions
    User createdUser = JsonUtil.toInstance(result.getResponse().getContentAsString(), User.class);
    assertTrue(user.equals(createdUser));

    // Controller call should reach the service
    verify(userService).saveEntity(isA(User.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_Add_Parent_Failure() throws Exception {
    User user = Mocker.getUser(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                post("/users") //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(user))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  public void test_Add_Anonymous_Failure() throws Exception {
    User user = Mocker.getUser(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                post("/users") //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(user))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_Delete_Admin_Success() throws Exception {
    // Service mock definition
    doNothing().when(userService).deleteEntity(isA(Long.class));

    mockMvc
        .perform( //
            delete("/users/1") //
                .contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(status().isNoContent()) //
        .andReturn();

    // Controller call should reach the service
    verify(userService).deleteEntity(isA(Long.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_Delete_Parent_Failure() throws Exception {
    mockMvc
        .perform( //
            delete("/users/1") //
                .contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(status().is3xxRedirection()) //
        .andReturn();
  }

  @Test
  public void test_Delete_Anonymous_Failure() throws Exception {
    mockMvc
        .perform( //
            delete("/users/1") //
                .contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(status().is3xxRedirection()) //
        .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_Update_Admin_Success() throws Exception {
    User user = Mocker.getUser(1L);
    Optional<User> optionalUser = Optional.of(user);

    // Service mock definition
    given(userService.updateEntity(isA(Long.class), isA(User.class))).willReturn(optionalUser);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                put("/users/" + user.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(user))) //
            .andExpect(status().isOk()) //
            .andReturn();

    // Controller response assertions
    User updatedUser = JsonUtil.toInstance(result.getResponse().getContentAsString(), User.class);
    assertTrue(user.equals(updatedUser));

    // Controller call should reach the service
    verify(userService).updateEntity(isA(Long.class), isA(User.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_Update_Parent_Failure() throws Exception {
    User user = Mocker.getUser(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                put("/users/" + user.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(user))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  public void test_Update_Anonymous_Failure() throws Exception {
    User user = Mocker.getUser(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                put("/users/" + user.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(user))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }
}
