package com.lavkesh.cloud.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = "com.lavkesh.cloud")
@EnableFeignClients
@EnableZuulProxy
public class ApiGatewayServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayServiceApplication.class, args);
  }
}
