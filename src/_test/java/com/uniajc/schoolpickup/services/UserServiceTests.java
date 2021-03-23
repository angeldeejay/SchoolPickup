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

import com.uniajc.schoolpickup.entities.User;
import com.uniajc.schoolpickup.repositories.UserRepository;
import com.uniajc.schoolpickup.util.Mocker;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

  @Mock private UserRepository repository;

  @InjectMocks private UserService service;

  @Test
  public void test_FindAllEntities_Success() throws Exception {
    List<User> users = new ArrayList<User>();
    for (int i = 0; i < 5; i++) {
      users.add(Mocker.getUser(Long.valueOf(i)));
    }

    when(repository.findAll()).thenReturn(users);

    List<User> foundUsers = service.findAllEntities();

    assertTrue(users.containsAll(foundUsers));

    verify(repository).findAll();
  }

  @Test
  public void test_FindEntityById_Success() throws Exception {
    User user = Mocker.getUser(1L);
    Optional<User> optionalUser = Optional.of(user);

    when(repository.findById(isA(Long.class))).thenReturn(optionalUser);

    Optional<User> foundUser = service.findEntityById(1L);
    assertTrue(user.equals(foundUser.get()));

    verify(repository).findById(isA(Long.class));
  }

  @Test
  public void test_SaveEntity_Success() throws Exception {
    User user = Mocker.getUser(1L);

    when(repository.save(any(User.class))).thenReturn(user);

    User created = service.saveEntity(user);

    assertThat(created.equals(user));
    verify(repository).save(user);
  }

  @Test
  public void test_DeleteEntity_Success() throws Exception {
    User user = Mocker.getUser(1L);
    Optional<User> optionalUser = Optional.of(user);

    when(repository.findById(isA(Long.class))).thenReturn(optionalUser);
    doNothing().when(repository).deleteById(isA(Long.class));

    service.deleteEntity(1L);

    verify(repository).findById(isA(Long.class));
    verify(repository).deleteById(isA(Long.class));
  }

  @Test
  public void test_UpdateEntity_Success() throws Exception {
    User user = Mocker.getUser(1L);
    Optional<User> optionalUser = Optional.of(user);

    when(repository.findById(isA(Long.class))).thenReturn(optionalUser);
    when(repository.save(isA(User.class))).thenReturn(user);

    Optional<User> updatedUser = service.updateEntity(user.getId(), user);
    assertTrue(user.equals(updatedUser.get()));

    verify(repository).findById(isA(Long.class));
    verify(repository).save(isA(User.class));
  }
}
