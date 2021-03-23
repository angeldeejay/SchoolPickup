package com.uniajc.schoolpickup.generics;

import com.uniajc.schoolpickup.security.CustomUserDetails;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class GenericController<T> {

  @Autowired HttpServletRequest request;

  public abstract List<T> getAll();

  public abstract Optional<T> getById(Long id);

  public abstract T add(T entity);

  public abstract void delete(Long id);

  public abstract Optional<T> update(Long id, T entity);

  public abstract String test();

  protected CustomUserDetails getUserDetails() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }
    return (CustomUserDetails) authentication.getPrincipal();
  }
}
