package com.uniajc.schoolpickup.beans;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@SuppressWarnings("deprecation")
@Named("DialogHandler")
@RequestScoped
public class DialogHandler {

  public void viewEntityForm(String form, String... rawParams) {
    Map<String, Object> options = new HashMap<>();
    options.put("resizable", false);
    options.put("modal", true);
    options.put("width", 640);
    options.put("contentWidth", "100%");
    options.put("contentHeight", "100%");

    Map<String, List<String>> params = new HashMap<>();
    params.put("params", Arrays.asList(rawParams));

    PrimeFaces.current()
        .dialog()
        .openDynamic("/templates/forms/" + form + ".xhtml", options, params);
  }
}