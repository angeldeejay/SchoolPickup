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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private DataSource dataSource;

  @Bean({"customUserDetailsService", "userDetailsService"})
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
    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String loginPage = "/login";
    String registerPage = "/register";
    String logoutPage = "/logout";

    http.csrf()
        .disable() //
        .headers()
        .defaultsDisabled()
        .cacheControl();
    ;
    http.userDetailsService(userDetailsService())
        .authorizeRequests()
        .antMatchers("/users/", "/users*", "/users**", "/users/*", "/users/*/**")
        .hasAuthority("ADMIN")
        .antMatchers("/parents/", "/parents*", "/parents**", "/parents/*", "/parents/*/**")
        .hasAuthority("ADMIN")
        .antMatchers(
            "/dashboard/",
            "/dashboard*",
            "/dashboard**",
            "/dashboard/*",
            "/dashboard/*/**",
            "/students/",
            "/students*",
            "/students**",
            "/students/*",
            "/students/*/**",
            "/pickup-requests/",
            "/pickup-requests*",
            "/pickup-requests**",
            "/pickup-requests/*",
            "/pickup-requests/*/**")
        .hasAnyAuthority("ADMIN", "PARENT")
        .antMatchers("/", loginPage + "**", registerPage + "**")
        .permitAll()
        .antMatchers("/resources/**", "/**", "/javax.faces.resource/**")
        .permitAll()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage(loginPage)
        .loginProcessingUrl(loginPage)
        .permitAll()
        .failureUrl(loginPage + "?error=true")
        .defaultSuccessUrl("/dashboard")
        .usernameParameter("username_hinput")
        .passwordParameter("password")
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
        .logoutSuccessUrl(loginPage)
        .and()
        .httpBasic()
        .disable()
        .exceptionHandling();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/**"); // #3
  }
}
