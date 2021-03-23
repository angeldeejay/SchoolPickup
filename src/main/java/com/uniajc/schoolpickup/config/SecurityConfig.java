package com.uniajc.schoolpickup.config;

import com.uniajc.schoolpickup.security.CustomUserDetailsService;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private DataSource dataSource;

  @Bean({"customUserDetailsService", "customUserDetailsService"})
  @Override
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService();
  }

  @Bean("passwordEncoder")
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean("authenticationManager")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean("authenticationProvider")
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    System.out.println(passwordEncoder().encode("andres72106"));

    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    System.out.println(PathRequest.toStaticResources().atCommonLocations());
    http.csrf()
        .disable() //
        .headers()
        .defaultsDisabled()
        .cacheControl();
    ;
    http.userDetailsService(userDetailsService())
        .authorizeRequests()
        .antMatchers("/resources/**")
        .permitAll()
        .antMatchers("/", "/**", "/javax.faces.resource/**")
        .permitAll()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .failureUrl("/login?error=true")
        .defaultSuccessUrl("/dashboard")
        .and()
        .logout()
        .logoutSuccessUrl("/login");

    // String loginPage = "/login";
    // String logoutPage = "/logout";
    // http
    //        .authorizeRequests()
    //        .antMatchers("/users/", "/users*", "/users**", "/users/*", "/users/*/**")
    //        .hasAnyAuthority("ADMIN")
    //        .antMatchers("/parents/", "/parents*", "/parents**", "/parents/*",
    // "/parents/*/**")
    //        .hasAnyAuthority("ADMIN")
    //        .antMatchers("/students/", "/students*", "/students**", "/students/*",
    // "/students/*/**")
    //        .hasAnyAuthority("ADMIN", "PARENT")
    //        .antMatchers(
    //            "/pickup-requests/",
    //            "/pickup-requests*",
    //            "/pickup-requests**",
    //            "/pickup-requests/*",
    //            "/pickup-requests/*/**")
    //        .hasAnyAuthority("ADMIN", "PARENT")
    //        .antMatchers("/", "/login**", "/signup**")
    //        .permitAll()
    //        .anyRequest()
    //        .authenticated()
    //        .and()
    //        .formLogin()
    //        .loginPage("/login")
    //        .failureUrl("/login-error")
    //        .and()
    //        .httpBasic()
    //        .disable();
    // .authorizeRequests()
    // .antMatchers("/javax.faces.resource/**")
    // .permitAll()
    // .antMatchers("/")
    // .permitAll()
    // .antMatchers(loginPage)
    // .permitAll()
    // .antMatchers("/registration")
    // .permitAll()
    // .antMatchers("/admin/**")
    // .hasAuthority("ADMIN")
    // .anyRequest()
    // .authenticated()
    // .and()
    // .csrf()
    // .disable()
    // .formLogin()
    // .loginPage(loginPage)
    // .failureUrl("/login?error=true")
    // .defaultSuccessUrl("/admin/home")
    // .usernameParameter("user_name")
    // .passwordParameter("password")
    // .and()
    // .logout()
    // .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
    // .logoutSuccessUrl(loginPage)
    // .and()
    // .exceptionHandling();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/**"); // #3
  }
}
