package com.lavkesh.cloud.securityService.config;

import com.lavkesh.cloud.security.auth.jwt.JwtAuthenticationToken;
import com.lavkesh.cloud.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.lavkesh.cloud.security.model.JwtToken;
import com.lavkesh.cloud.security.model.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfiguration {

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public RequestInterceptor requestTokenBearerInterceptor() {

    return new RequestInterceptor() {
      @Override
      public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof JwtAuthenticationToken) {
          JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
          UserContext userContext = (UserContext) authenticationToken.getPrincipal();
          JwtToken token = userContext.getJwtToken();
          String jwtTokenHeaderParam = JwtTokenAuthenticationProcessingFilter.JWT_TOKEN_HEADER_PARAM;
          requestTemplate.header(jwtTokenHeaderParam, "Bearer " + token.getToken());
        }
      }
    };
  }
}
