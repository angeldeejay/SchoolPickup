package com.uniajc.schoolpickup.util;

import com.github.javafaker.Faker;
import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.entities.User;

public class Mocker {

  private static Faker faker = new Faker();

  public static Parent getParent(Long id) {
    Parent parent = new Parent();
    parent.setId(id);
    parent.setFirstName(faker.name().firstName());
    parent.setLastName(faker.name().lastName());
    parent.setIdentificationType("CC");
    parent.setIdentificationValue(faker.number().digits(11));
    return parent;
  }

  public static User getUser(Long id) {
    User user = new User();
    user.setId(id);
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    user.setParent(getParent(id));
    return user;
  }

  public static Student getStudent(Long id) {
    Student student = new Student();
    student.setId(id);
    student.setFirstName(faker.name().firstName());
    student.setLastName(faker.name().lastName());
    student.setParent(getParent(id));
    return student;
  }

  public static PickupRequest getPickupRequest(Long id) {
    PickupRequest pickupRequest = new PickupRequest();
    pickupRequest.setId(id);
    pickupRequest.setSlot(Integer.parseInt(faker.number().digit()));
    pickupRequest.setParent(getParent(id));
    pickupRequest.setStudent(getStudent(id));
    return pickupRequest;
  }
}
