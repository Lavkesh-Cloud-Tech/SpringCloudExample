package com.lavkesh.cloud.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Created by RATHIL on 7/19/2017.
 */
public interface CustomWebSecurityConfiguaration {

  void configure(AuthenticationManagerBuilder auth);

  void addRequestMatcher(HttpSecurity http) throws Exception;

  void addFilterBefore(HttpSecurity http) throws Exception;

  void addFilterAfter(HttpSecurity http) throws Exception;

  AuthenticationManager getAuthenticationManager();

  void setAuthenticationManager(AuthenticationManager authenticationManager);

  AuthenticationFailureHandler getFailureHandler();

  void setFailureHandler(AuthenticationFailureHandler failureHandler);
}
