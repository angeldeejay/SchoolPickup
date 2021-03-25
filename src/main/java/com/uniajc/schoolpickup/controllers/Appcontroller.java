package com.uniajc.schoolpickup.controllers;

import com.uniajc.schoolpickup.util.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Appcontroller extends AbstractController {
  @RequestMapping(
      value = {"/", "/login"},
      method = RequestMethod.GET)
  public String login() {
    if (isAuthenticated()) {
      return "redirect:/dashboard";
    }
    return "index.xhtml";
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String registerForm(Model model) {
    if (isAuthenticated()) {
      return "redirect:/dashboard";
    }
    return "register.xhtml";
  }

  @Override
  @RequestMapping("/dashboard")
  public String viewDashboard(Model model, HttpServletRequest request) {
    this.setSessionModel(model, request);
    return "home-dashboard.xhtml";
  }
}
