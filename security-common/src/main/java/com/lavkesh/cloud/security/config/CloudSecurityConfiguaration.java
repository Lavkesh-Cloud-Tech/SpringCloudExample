package com.lavkesh.cloud.security.config;

import com.lavkesh.cloud.security.RestAuthenticationEntryPoint;
import com.lavkesh.cloud.security.auth.AnonymousRequestMatcher;
import com.lavkesh.cloud.security.auth.AuthenticateRequestMatcher;
import com.lavkesh.cloud.security.auth.jwt.JwtAuthenticationProvider;
import com.lavkesh.cloud.security.auth.jwt.JwtRequestMatcher;
import com.lavkesh.cloud.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ConditionalOnProperty(prefix = "lavkesh.security", name = "enabled")
public class CloudSecurityConfiguaration extends WebSecurityConfigurerAdapter {

  @Autowired
  private RestAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private AuthenticationFailureHandler failureHandler;

  @Autowired
  private AnonymousRequestMatcher anonymousRequestMatcher;

  @Autowired
  private AuthenticateRequestMatcher authenticateRequestMatcher;

  @Autowired
  private JwtRequestMatcher jwtRequestMatcher;

  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  @Autowired
  private CustomWebSecurityConfiguaration customWebSecurityConfiguaration;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(jwtAuthenticationProvider);
    //Providing way for other user to add their authentication provider
    customWebSecurityConfiguaration.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = authenticationManagerBean();
    JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationFilter =
        buildJwtTokenAuthenticationProcessingFilter(authenticationManager);

    customWebSecurityConfiguaration.setAuthenticationManager(authenticationManager);
    customWebSecurityConfiguaration.setFailureHandler(failureHandler);

    http.csrf().disable()
        .httpBasic().disable()
        .formLogin().disable()
        .exceptionHandling()
        .authenticationEntryPoint(this.authenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .requestMatchers(anonymousRequestMatcher)
        .permitAll() // Login end-point
        .requestMatchers(authenticateRequestMatcher)
        .authenticated(); // Protected API End-points

    //Providing way for other user to add their request matcher
    customWebSecurityConfiguaration.addRequestMatcher(http);

    //Providing way for other user to add their filter before jwtTokenAuthenticationFilter
    customWebSecurityConfiguaration.addFilterBefore(http);

    http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    //Providing way for other user to add their filter after jwtTokenAuthenticationFilter
    customWebSecurityConfiguaration.addFilterAfter(http);
  }

  protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(
      AuthenticationManager authenticationManager)
      throws Exception {
    JwtTokenAuthenticationProcessingFilter filter =
        new JwtTokenAuthenticationProcessingFilter(failureHandler, jwtRequestMatcher);
    filter.setAuthenticationManager(authenticationManager);
    return filter;
  }
}
