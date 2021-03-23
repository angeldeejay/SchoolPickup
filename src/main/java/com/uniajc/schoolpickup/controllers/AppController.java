package com.uniajc.schoolpickup.controllers;

import com.uniajc.schoolpickup.entities.User;
import java.util.List;

import com.uniajc.schoolpickup.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {

  @Autowired private UserRepository repository;

  // Login form
  @GetMapping({"", "/login"})
  public String login() {
    return "login-form";
  }

  @PostMapping("/login")
  @ResponseBody
  public String loginProcess(User user) {
    return "You made it!";
  }

  // Login form with error
  @RequestMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return "login";
  }

  @GetMapping("/signup")
  public String viewSignUp(Model model) {
    model.addAttribute("user", new User());

    return "signup-form";
  }

  @PostMapping("/signup")
  public String processSignUp(User user) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);

    repository.save(user);

    return "signup-success";
  }

  @GetMapping("/dashboard")
  public String viewDashboard(Model model) {
    List<User> users = repository.findAll();
    model.addAttribute("users", users);

    return "dashboard";
  }
}
