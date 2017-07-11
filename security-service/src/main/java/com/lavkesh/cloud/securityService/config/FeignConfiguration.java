package com.lavkesh.cloud.securityService.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
