package com.uniajc.schoolpickup.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.javafaker.Faker;
import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.repositories.ParentRepository;

@RunWith(MockitoJUnitRunner.class)
public class ParentServiceTests {

	@Mock
	private ParentRepository repository;

	@InjectMocks
	private ParentService service;

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

		when(repository.findAll()).thenReturn(parents);

		List<Parent> foundParents = service.findAllEntities();

		assertTrue(parents.containsAll(foundParents));

		verify(repository).findAll();
	}

	@Test
	public void test_FindEntityById_Success() throws Exception {
		Parent parent = getDummy(1L);
		Optional<Parent> optionalParent = Optional.of(parent);

		when(repository.findById(isA(Long.class))).thenReturn(optionalParent);

		Optional<Parent> foundParent = service.findEntityById(1L);
		assertTrue(parent.equals(foundParent.get()));

		verify(repository).findById(isA(Long.class));
	}

	@Test
	public void test_SaveEntity_Success() throws Exception {
		Parent parent = getDummy(1L);

		when(repository.save(any(Parent.class))).thenReturn(parent);

		Parent created = service.saveEntity(parent);

		assertThat(created.getFirstName()).isSameAs(parent.getFirstName());
		verify(repository).save(parent);
	}

	@Test
	public void test_DeleteEntity_Success() throws Exception {
		Parent parent = getDummy(1L);
		Optional<Parent> optionalParent = Optional.of(parent);

		when(repository.findById(isA(Long.class))).thenReturn(optionalParent);
		doNothing().when(repository).deleteById(isA(Long.class));

		service.deleteEntity(1L);

		verify(repository).findById(isA(Long.class));
		verify(repository).deleteById(isA(Long.class));
	}

	@Test
	public void test_UpdateEntity_Success() throws Exception {
		Parent parent = getDummy(1L);
		Optional<Parent> optionalParent = Optional.of(parent);

		when(repository.findById(isA(Long.class))).thenReturn(optionalParent);
		when(repository.save(isA(Parent.class))).thenReturn(parent);

		Optional<Parent> updatedParent = service.updateEntity(parent.getId(), parent);
		assertTrue(parent.equals(updatedParent.get()));

		verify(repository).findById(isA(Long.class));
		verify(repository).save(isA(Parent.class));
	}
}