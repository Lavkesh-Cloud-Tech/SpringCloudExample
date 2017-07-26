package com.lavkesh.cloud.securityService.security;

import com.lavkesh.cloud.securityService.security.model.AjaxAuthenticationConfig;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

  @Bean
  @RefreshScope
  public AjaxAuthenticationConfig getAjaxAuthenticationConfig(){
    return new AjaxAuthenticationConfig();
  }

}
