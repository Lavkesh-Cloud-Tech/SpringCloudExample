package com.lavkesh.cloud.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Created by RATHIL on 7/19/2017.
 */
@Component
@ConditionalOnMissingBean(CustomWebSecurityConfiguaration.class)
public class CustomWebSecurityConfiguarationAdapter implements CustomWebSecurityConfiguaration {

  private AuthenticationManager authenticationManager;
  private AuthenticationFailureHandler failureHandler;

  @Override
  public void configure(AuthenticationManagerBuilder auth) {
  }

  @Override
  public void addRequestMatcher(HttpSecurity http) throws Exception {
  }

  @Override
  public void addFilterBefore(HttpSecurity http) throws Exception {
  }

  @Override
  public void addFilterAfter(HttpSecurity http) throws Exception {
  }

  public AuthenticationManager getAuthenticationManager() {
    return authenticationManager;
  }

  public void setAuthenticationManager(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public AuthenticationFailureHandler getFailureHandler() {
    return failureHandler;
  }

  public void setFailureHandler(
      AuthenticationFailureHandler failureHandler) {
    this.failureHandler = failureHandler;
  }
}
