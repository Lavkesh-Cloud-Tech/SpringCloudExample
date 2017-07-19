package com.lavkesh.cloud.securityService;

import com.lavkesh.cloud.security.WebSecurityConfiguaration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.lavkesh.cloud.securityService.feign")
@Import(WebSecurityConfiguaration.class)
public class SecurityServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityServiceApplication.class, args);
  }
}
