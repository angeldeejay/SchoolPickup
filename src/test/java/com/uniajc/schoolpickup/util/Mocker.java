package com.uniajc.schoolpickup.util;

import com.github.javafaker.Faker;
import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.User;
import com.uniajc.schoolpickup.generics.GenericEntity;

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

}
