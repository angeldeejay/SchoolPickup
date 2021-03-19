package com.uniajc.schoolpickup.views;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class HelloWorldBean implements Serializable {

    public String getMessage() {
        return "Hello World";
    }

}
