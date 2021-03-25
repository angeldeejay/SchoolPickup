package com.uniajc.schoolpickup.beans;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.User;
import com.uniajc.schoolpickup.services.ParentService;
import com.uniajc.schoolpickup.services.UserService;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Named
@RequestScoped
public class UserRegisterBean {

  @Autowired private UserService userService;
  @Autowired private ParentService parentService;

  private User user = new User();
  private Parent parent = new Parent();

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Parent getParent() {
    return parent;
  }

  public void setParent(Parent parent) {
    this.parent = parent;
  }

  public void register() {
    FacesContext context = FacesContext.getCurrentInstance();
    try {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      User savedUser = userService.saveEntity(user);

      parent.setUser(savedUser);
      parentService.saveEntity(parent);

      context.getExternalContext().redirect("/login?registered=true");
    } catch (Exception ex) {
      context.addMessage(null, new FacesMessage("The parent was'n registered: " + user.toString()));
    }
  }
}