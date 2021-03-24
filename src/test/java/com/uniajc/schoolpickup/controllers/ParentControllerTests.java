package com.uniajc.schoolpickup.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.security.CustomUserDetails;
import com.uniajc.schoolpickup.security.WithMockAuthorityUser;
import com.uniajc.schoolpickup.services.ParentService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
@ContextConfiguration
@WebAppConfiguration
public class ParentControllerTests {
  @MockBean private ParentService parentService;

  @Autowired private WebApplicationContext context;

  @Autowired private Filter springSecurityFilterChain;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(springSecurityFilterChain))
            .build();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_FindAllEntities_Admin_Success() throws Exception {
    List<Parent> parents = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      parents.add(Mocker.getParent(Long.valueOf(i)));
    }

    // Service mock definition
    given(parentService.findAllEntities()).willReturn(parents);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/parents") //
                    .with(user(new CustomUserDetails(Mocker.getUser(1L)))) //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andReturn();

    // Controller response assertions
    String jsonResponse = result.getResponse().getContentAsString();
    List<Parent> foundParents =
        JsonUtil.toInstances(jsonResponse, new TypeReference<List<Parent>>() {});

    assertTrue(parents.containsAll(foundParents));

    // Controller call should reach the service
    verify(parentService).findAllEntities();
  }

  @WithMockAuthorityUser("parent")
  public void test_FindAllEntities_Parent_Failure() throws Exception {
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/parents") //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  public void test_FindAllEntities_Anonymous_Failure() throws Exception {
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/parents") //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_GetById_Admin_Success() throws Exception {
    Parent parent = Mocker.getParent(1L);
    Optional<Parent> optionalParent = Optional.of(parent);

    // Service mock definition
    given(parentService.findEntityById(isA(Long.class))).willReturn(optionalParent);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/parents/" + parent.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andReturn();

    // Controller response assertions
    Parent foundParent =
        JsonUtil.toInstance(result.getResponse().getContentAsString(), Parent.class);
    assertTrue(parent.equals(foundParent));

    // Controller call should reach the service
    verify(parentService).findEntityById(isA(Long.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_GetById_Parent_Failure() throws Exception {
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/parents/1") //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  public void test_GetById_Anonymous_Failure() throws Exception {
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                get("/parents/1") //
                    .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_Add_Admin_Success() throws Exception {
    Parent parent = Mocker.getParent(1L);

    // Service mock definition
    given(parentService.saveEntity(isA(Parent.class))).willReturn(parent);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                post("/parents") //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(parent))) //
            .andExpect(status().isCreated()) //
            .andReturn();

    // Controller response assertions
    Parent createdParent =
        JsonUtil.toInstance(result.getResponse().getContentAsString(), Parent.class);
    assertTrue(parent.equals(createdParent));

    // Controller call should reach the service
    verify(parentService).saveEntity(isA(Parent.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_Add_Parent_Failure() throws Exception {
    Parent parent = Mocker.getParent(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                post("/parents") //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(parent))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  public void test_Add_Anonymous_Failure() throws Exception {
    Parent parent = Mocker.getParent(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                post("/parents") //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(parent))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_Delete_Admin_Success() throws Exception {
    // Service mock definition
    doNothing().when(parentService).deleteEntity(isA(Long.class));

    mockMvc
        .perform( //
            delete("/parents/1") //
                .contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(status().isNoContent()) //
        .andReturn();

    // Controller call should reach the service
    verify(parentService).deleteEntity(isA(Long.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_Delete_Parent_Failure() throws Exception {
    mockMvc
        .perform( //
            delete("/parents/1") //
                .contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(status().is3xxRedirection()) //
        .andReturn();
  }

  @Test
  public void test_Delete_Anonymous_Failure() throws Exception {
    mockMvc
        .perform( //
            delete("/parents/1") //
                .contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(status().is3xxRedirection()) //
        .andReturn();
  }

  @Test
  @WithMockAuthorityUser("admin")
  public void test_Update_Admin_Success() throws Exception {
    Parent parent = Mocker.getParent(1L);
    Optional<Parent> optionalParent = Optional.of(parent);

    // Service mock definition
    given(parentService.updateEntity(isA(Long.class), isA(Parent.class)))
        .willReturn(optionalParent);

    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                put("/parents/" + parent.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(parent))) //
            .andExpect(status().isOk()) //
            .andReturn();

    // Controller response assertions
    Parent updatedParent =
        JsonUtil.toInstance(result.getResponse().getContentAsString(), Parent.class);
    assertTrue(parent.equals(updatedParent));

    // Controller call should reach the service
    verify(parentService).updateEntity(isA(Long.class), isA(Parent.class));
  }

  @Test
  @WithMockAuthorityUser("parent")
  public void test_Update_Parent_Failure() throws Exception {
    Parent parent = Mocker.getParent(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                put("/parents/" + parent.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(parent))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }

  @Test
  public void test_Update_Anonymous_Failure() throws Exception {
    Parent parent = Mocker.getParent(1L);
    // Controller request assertions
    MvcResult result =
        mockMvc
            .perform( //
                put("/parents/" + parent.getId().toString()) //
                    .contentType(MediaType.APPLICATION_JSON) //
                    .content(JsonUtil.toJson(parent))) //
            .andExpect(status().is3xxRedirection()) //
            .andReturn();
  }
}
