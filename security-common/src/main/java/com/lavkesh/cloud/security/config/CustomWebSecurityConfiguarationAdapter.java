package com.lavkesh.cloud.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/** Created by RATHIL on 7/19/2017. */
@Component
@ConditionalOnMissingBean(CustomWebSecurityConfiguaration.class)
public class CustomWebSecurityConfiguarationAdapter implements CustomWebSecurityConfiguaration {

  @Override
  public void configure(AuthenticationManagerBuilder auth) {}

  @Override
  public void addRequestMatcher(HttpSecurity http) throws Exception {}

  @Override
  public void addFilterBefore(HttpSecurity http) throws Exception {}

  @Override
  public void addFilterAfter(HttpSecurity http) throws Exception {}
}
