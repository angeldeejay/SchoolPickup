
package com.uniajc.schoolpickup.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SuppressWarnings("deprecation")
@Named("NotificationHandler")
@RequestScoped
public class NotificationHandler {
  /**
   * Shows a notification in the dashboard
   * @param severity
   * @param summary
   * @param detail 
   */
  public void show (FacesMessage.Severity severity, String summary, String detail) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
  }

  public void showInfo(String detail) {
    this.show(FacesMessage.SEVERITY_INFO, "Información", detail);
  }

  public void showInfo(String summary, String detail) {
    this.show(FacesMessage.SEVERITY_INFO, summary, detail);
  }

  public void showWarning(String detail) {
    this.show(FacesMessage.SEVERITY_WARN, "Advertencia", detail);
  }

  public void showWarning(String summary, String detail) {
    this.show(FacesMessage.SEVERITY_WARN, summary, detail);
  }

  public void showError(String detail) {
    this.show(FacesMessage.SEVERITY_ERROR, "Error", detail);
  }

  public void showError(String summary, String detail) {
    this.show(FacesMessage.SEVERITY_ERROR, summary, detail);
  }
}