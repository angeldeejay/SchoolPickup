package com.uniajc.schoolpickup.generics;

import static com.uniajc.schoolpickup.util.Inflector.pascalize;
import static java.lang.String.join;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public abstract class GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        ArrayList<String> fieldsData = new ArrayList<>();
        Class<? extends GenericEntity> klazz = this.getClass();
        for (Field attribute : klazz.getDeclaredFields()) {
            int mod = attribute.getModifiers();
            if (Modifier.isAbstract(mod) || Modifier.isFinal(mod) | Modifier.isStatic(mod)) {
                continue;
            }
            try {
                boolean accessible = attribute.canAccess(this);

                attribute.setAccessible(true);
                String prefix = "get";
                Method getter = klazz.getDeclaredMethod(prefix + pascalize(attribute.getName()), new Class[] {});

                fieldsData.add(attribute.getName() + "=" + getter.invoke(this));
                attribute.setAccessible(accessible);
            } catch (IllegalAccessException | IllegalArgumentException | SecurityException
                    | InvocationTargetException ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            } catch (NoSuchMethodException ex) {
                // Skip
            }
        }
        return this.getClass().getName() + "{" + join(", ", fieldsData.toArray(new String[fieldsData.size()])) + "}";
    }

    @Override
    public boolean equals(Object object) {
        return object == null ? false : toString().equals(object.toString());
    }
}
