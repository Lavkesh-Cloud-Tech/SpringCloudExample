package com.lavkesh.cloud.securityService.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavkesh.cloud.security.auth.CustomAuthenticationFailureHandler;
import com.lavkesh.cloud.securityService.security.ajax.AjaxAuthenticationProvider;
import com.lavkesh.cloud.securityService.security.ajax.AjaxAwareAuthenticationSuccessHandler;
import com.lavkesh.cloud.securityService.security.ajax.AjaxLoginProcessingFilter;
import com.lavkesh.cloud.securityService.security.ajax.AjaxRequestMatcher;
import com.lavkesh.cloud.security.config.CustomWebSecurityConfiguarationAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceCustomWebSecurityConfiguaration
    extends CustomWebSecurityConfiguarationAdapter {

  @Autowired private CustomAuthenticationFailureHandler failureHandler;

  @Autowired private AjaxRequestMatcher ajaxRequestMatcher;
  @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
  @Autowired private AjaxAwareAuthenticationSuccessHandler successHandler;

  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private ObjectMapper objectMapper;

  protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
    AjaxLoginProcessingFilter filter =
        new AjaxLoginProcessingFilter(
            successHandler, failureHandler, objectMapper, ajaxRequestMatcher);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(ajaxAuthenticationProvider);
  }

  @Override
  public void addRequestMatcher(HttpSecurity http) throws Exception {
    http.authorizeRequests().requestMatchers(ajaxRequestMatcher).authenticated();
  }

  @Override
  public void addFilterBefore(HttpSecurity http) throws Exception {
    AjaxLoginProcessingFilter ajaxLoginProcessingFilter = buildAjaxLoginProcessingFilter();
    http.addFilterBefore(ajaxLoginProcessingFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
