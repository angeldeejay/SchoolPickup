package com.uniajc.schoolpickup.views;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class ConfirmView {

  public void confirm() {
    addMessage("Confirmed", "You have accepted");
  }

  public void delete() {
    addMessage("Confirmed", "Record deleted");
  }

  public void addMessage(String summary, String detail) {
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
    FacesContext.getCurrentInstance().addMessage(null, message);
  }
}