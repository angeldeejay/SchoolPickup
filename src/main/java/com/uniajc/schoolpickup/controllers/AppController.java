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

@Controller
public class AppController {

    @Autowired
    private UserRepository repository;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
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
