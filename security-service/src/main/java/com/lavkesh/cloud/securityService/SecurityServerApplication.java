package com.lavkesh.cloud.securityService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.lavkesh.cloud.securityService.feign")
public class SecurityServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityServerApplication.class, args);
  }
}
