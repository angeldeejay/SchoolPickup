package com.uniajc.schoolpickup.util;

import com.github.javafaker.Faker;
import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.entities.User;

public class Mocker {

  private static final Faker faker = new Faker();

  public static Parent getParent(Long id) {
    Parent entity = new Parent();
    entity.setId(id);
    entity.setEmail(faker.internet().emailAddress());
    entity.setPhone(faker.phoneNumber().cellPhone());
    entity.setUser(getUser(id));
    return entity;
  }

  public static User getUser(Long id) {
    User entity = new User();
    entity.setId(id);
    entity.setFirstName(faker.name().firstName());
    entity.setLastName(faker.name().lastName());
    entity.setIdentification(faker.number().digits(11));
    entity.setPassword(faker.internet().password());
    return entity;
  }

  public static Student getStudent(Long id) {
    Student entity = new Student();
    entity.setId(id);
    entity.setFirstName(faker.name().firstName());
    entity.setLastName(faker.name().lastName());
    entity.setParent(getParent(id));
    return entity;
  }

  public static PickupRequest getPickupRequest(Long id) {
    PickupRequest entity = new PickupRequest();
    entity.setId(id);
    entity.setSlot(Integer.parseInt(faker.number().digit()));
    entity.setParent(getParent(id));
    entity.setStudent(getStudent(id));
    return entity;
  }
}
