package com.uniajc.schoolpickup.util;

import com.uniajc.schoolpickup.security.CustomUserDetails;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public class AbstractController {
  protected boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
      return false;
    }
    return authentication.isAuthenticated();
  }

  protected void setSessionModel(Model model, HttpServletRequest request) {
    String routePath = request.getRequestURI();
    String routePrefix = Arrays.asList(routePath.substring(1).split("/")).get(0);
    model.addAttribute("routePath", routePath);
    model.addAttribute("routePrefix", routePrefix);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
      return;
    }
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    model.addAttribute("sessionData", userDetails);
    model.addAttribute("loggedInUser", userDetails.getUser());
  }

  // Routes: /**/dashboard
  @RequestMapping("/dashboard")
  public String viewDashboard(Model model, HttpServletRequest request) {
    this.setSessionModel(model, request);
    return model.getAttribute("routePrefix") + "-dashboard.xhtml";
  }

  // Route: GET /**/test
  @ResponseBody
  @RequestMapping(
      value = "/test",
      method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public String test() {
    return "Test done";
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(HttpServletRequest request, Exception ex) {
    ModelAndView modelAndView = new ModelAndView();
    System.out.println(ex);
    System.out.println(request);
    modelAndView.addObject("exception", ex);
    modelAndView.addObject("url", request.getRequestURL());
    modelAndView.setViewName("error-view-name");
    return modelAndView;
  }
}
