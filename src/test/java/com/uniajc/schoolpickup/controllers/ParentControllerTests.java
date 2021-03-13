package com.uniajc.schoolpickup.controllers;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.javafaker.Faker;
import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.services.ParentService;
import com.uniajc.schoolpickup.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParentControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ParentService parentService;

	private Faker faker = new Faker();

	private Parent getDummy(Long id) {
		Parent parent = new Parent();
		parent.setId(id);
		parent.setFirstName(faker.name().firstName());
		parent.setLastName(faker.name().lastName());
		parent.setIdentificationType("CC");
		parent.setIdentificationValue(faker.number().digits(11));
		parent.setEmail(faker.internet().emailAddress());
		parent.setPassword(faker.internet().password());
		return parent;
	}

	@Test
	public void test_FindAllEntities_Success() throws Exception {
		List<Parent> parents = new ArrayList<Parent>();
		for (int i = 0; i < 5; i++) {
			parents.add(getDummy(Long.valueOf(i)));
		}

		// Service mock definition
		given(parentService.findAllEntities()).willReturn(parents);

		// Controller request assertions
		MvcResult result = mockMvc.perform( //
				get("/parents").contentType(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isOk()) //
				.andReturn();

		// Controller response assertions
		String jsonResponse = result.getResponse().getContentAsString();
		List<Parent> foundParents = JsonUtil.toInstances(jsonResponse, new TypeReference<List<Parent>>() {
		});

		assertTrue(parents.containsAll(foundParents));

		// Controller call should reach the service
		verify(parentService).findAllEntities();
	}

	@Test
	public void test_GetById_Success() throws Exception {
		Parent parent = getDummy(1L);
		Optional<Parent> optionalParent = Optional.of(parent);

		// Service mock definition
		given(parentService.findEntityById(isA(Long.class))).willReturn(optionalParent);

		// Controller request assertions
		MvcResult result = mockMvc.perform( //
				get("/parents/" + parent.getId().toString()) //
						.contentType(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isOk()) //
				.andReturn();

		// Controller response assertions
		Parent foundParent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Parent.class);
		assertTrue(parent.equals(foundParent));

		// Controller call should reach the service
		verify(parentService).findEntityById(isA(Long.class));
	}

	@Test
	public void test_Add_Success() throws Exception {
		Parent parent = getDummy(1L);

		// Service mock definition
		given(parentService.saveEntity(isA(Parent.class))).willReturn(parent);

		// Controller request assertions
		MvcResult result = mockMvc.perform( //
				post("/parents") //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(JsonUtil.toJson(parent))) //
				.andExpect(status().isCreated()) //
				.andReturn();

		// Controller response assertions
		Parent createdParent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Parent.class);
		assertTrue(parent.equals(createdParent));

		// Controller call should reach the service
		verify(parentService).saveEntity(isA(Parent.class));
	}

	@Test
	public void test_Delete_Success() throws Exception {
		// Service mock definition
		doNothing().when(parentService).deleteEntity(isA(Long.class));

		// Controller request assertions
		MvcResult result = mockMvc.perform( //
				delete("/parents/1") //
						.contentType(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isNoContent()) //
				.andReturn();

		// Controller call should reach the service
		verify(parentService).deleteEntity(isA(Long.class));
	}

	@Test
	public void test_Update_Success() throws Exception {
		Parent parent = getDummy(1L);
		Optional<Parent> optionalParent = Optional.of(parent);

		// Service mock definition
		given(parentService.updateEntity(isA(Long.class), isA(Parent.class))).willReturn(optionalParent);

		// Controller request assertions
		MvcResult result = mockMvc.perform( //
				put("/parents/" + parent.getId().toString()) //
						.contentType(MediaType.APPLICATION_JSON) //
						.content(JsonUtil.toJson(parent))) //
				.andExpect(status().isOk()) //
				.andReturn();

		// Controller response assertions
		Parent updatedParent = JsonUtil.toInstance(result.getResponse().getContentAsString(), Parent.class);
		assertTrue(parent.equals(updatedParent));

		// Controller call should reach the service
		verify(parentService).updateEntity(isA(Long.class), isA(Parent.class));
	}
}