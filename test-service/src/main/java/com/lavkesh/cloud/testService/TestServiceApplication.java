package com.lavkesh.cloud.testService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.lavkesh.cloud.testService.feign")
public class TestServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestServiceApplication.class, args);
  }
}
