package com.lavkesh.cloud.apigatewayservice.config;

import com.lavkesh.cloud.security.config.CustomWebSecurityConfiguarationAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class ApiGatewayWebSecurityConfiguaration
    extends CustomWebSecurityConfiguarationAdapter {

  @Override
  public void addFilterBefore(HttpSecurity http) throws Exception {
    http.addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class);
  }

}
