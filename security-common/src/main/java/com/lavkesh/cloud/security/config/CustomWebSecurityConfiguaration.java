package com.lavkesh.cloud.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/** Created by RATHIL on 7/19/2017. */
public interface CustomWebSecurityConfiguaration {

  void configure(AuthenticationManagerBuilder auth);

  void addRequestMatcher(HttpSecurity http) throws Exception;

  void addFilterBefore(HttpSecurity http) throws Exception;

  void addFilterAfter(HttpSecurity http) throws Exception;
}
