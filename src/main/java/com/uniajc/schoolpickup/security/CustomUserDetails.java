package com.uniajc.schoolpickup.security;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.User;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("unchecked")
public class CustomUserDetails extends org.springframework.security.core.userdetails.User
    implements UserDetails {

  private User user;

  public CustomUserDetails(User user) {
    super(
        user.getEmail(),
        user.getPassword(),
        CustomUserDetails.extractAuthoritiesFromUserDetails(user));
    this.user = user;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return (Collection<GrantedAuthority>)
        CustomUserDetails.extractAuthoritiesFromUserDetails(this.user);
  }

  protected static Collection<? extends GrantedAuthority> extractAuthoritiesFromUserDetails(
      User user) {
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (user != null) {
      if (user.hasAdminRole()) {
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
      }
      if (user.hasParentRole()) {
        authorities.add(new SimpleGrantedAuthority("PARENT"));
      }
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getFullName() {
    Parent parent = user.getParent();
    if (parent != null) {
      return parent.getFirstName() + ' ' + parent.getLastName();
    }
    return user.getEmail();
  }
}
