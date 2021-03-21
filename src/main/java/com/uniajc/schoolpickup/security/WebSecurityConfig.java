package com.uniajc.schoolpickup.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean({ "customUserDetailsService", "customUserDetailsService" })
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
        http.authorizeRequests().antMatchers("/", "/login**", "/signup**").permitAll()
                .antMatchers("/users/", "/users*", "/users**", "/users/*", "/users/*/**").hasAnyAuthority("ADMIN")
                .antMatchers("/parents/", "/parents*", "/parents**", "/parents/*", "/parents/*/**")
                .hasAnyAuthority("ADMIN")
                .antMatchers("/students/", "/students*", "/students**", "/students/*", "/students/*/**")
                .hasAnyAuthority("ADMIN", "PARENT")
                .antMatchers("/pickup-requests/", "/pickup-requests*", "/pickup-requests**", "/pickup-requests/*",
                        "/pickup-requests/*/**")
                .hasAnyAuthority("ADMIN", "PARENT").antMatchers("/**").hasAnyAuthority("ADMIN", "PARENT").and()
                .httpBasic().and().csrf().disable().formLogin().disable();
    }
}
