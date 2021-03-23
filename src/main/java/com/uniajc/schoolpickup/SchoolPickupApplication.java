package com.uniajc.schoolpickup;

import com.sun.faces.config.ConfigureListener;
import javax.faces.webapp.FacesServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchoolPickupApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchoolPickupApplication.class, args);
  }

  @Bean
  public FacesServlet facesServlet() {
    return new FacesServlet();
  }

  @Bean
  public ServletRegistrationBean facesServletRegistration() {
    ServletRegistrationBean registration = new ServletRegistrationBean(facesServlet(), "*.xhtml");
    registration.setName("FacesServlet");
    return registration;
  }

  @Bean
  public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
    return new ServletListenerRegistrationBean<>(new ConfigureListener());
  }
}
