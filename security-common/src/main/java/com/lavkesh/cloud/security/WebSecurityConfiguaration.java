package com.lavkesh.cloud.security;

import com.lavkesh.cloud.security.auth.AnonymousRequestMatcher;
import com.lavkesh.cloud.security.auth.AuthenticateRequestMatcher;
import com.lavkesh.cloud.security.auth.CustomAuthenticationFailureHandler;
import com.lavkesh.cloud.security.auth.jwt.JwtAuthenticationProvider;
import com.lavkesh.cloud.security.auth.jwt.JwtRequestMatcher;
import com.lavkesh.cloud.security.config.CustomWebSecurityConfiguaration;
import com.lavkesh.cloud.security.config.CustomWebSecurityConfiguarationAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class WebSecurityConfiguaration {

  @Bean
  @ConditionalOnMissingBean(RestAuthenticationEntryPoint.class)
  public RestAuthenticationEntryPoint getRestAuthenticationEntryPoint() {
    RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();
    return restAuthenticationEntryPoint;
  }

  @Bean
  @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
  public AuthenticationFailureHandler getAuthenticationFailureHandler() {
    AuthenticationFailureHandler authenticationFailureHandler = new CustomAuthenticationFailureHandler();
    return authenticationFailureHandler;
  }

  @Bean
  @ConditionalOnMissingBean(AnonymousRequestMatcher.class)
  public AnonymousRequestMatcher getAnonymousRequestMatcher() {
    AnonymousRequestMatcher anonymousRequestMatcher = new AnonymousRequestMatcher();
    return anonymousRequestMatcher;
  }

  @Bean
  @ConditionalOnMissingBean(AuthenticateRequestMatcher.class)
  public AuthenticateRequestMatcher getAuthenticateRequestMatcher() {
    AuthenticateRequestMatcher authenticateRequestMatcher = new AuthenticateRequestMatcher();
    return authenticateRequestMatcher;
  }

  @Bean
  @ConditionalOnMissingBean(JwtRequestMatcher.class)
  public JwtRequestMatcher getJwtRequestMatcher() {
    JwtRequestMatcher jwtRequestMatcher = new JwtRequestMatcher();
    return jwtRequestMatcher;
  }

  @Bean
  @ConditionalOnMissingBean(JwtAuthenticationProvider.class)
  public JwtAuthenticationProvider getJwtAuthenticationProvider() {
    JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
    return jwtAuthenticationProvider;
  }

  @Bean
  @ConditionalOnMissingBean(CustomWebSecurityConfiguaration.class)
  public CustomWebSecurityConfiguaration getCustomWebSecurityConfiguaration() {
    CustomWebSecurityConfiguaration customWebSecurityConfiguaration = new CustomWebSecurityConfiguarationAdapter();
    return customWebSecurityConfiguaration;
  }

}