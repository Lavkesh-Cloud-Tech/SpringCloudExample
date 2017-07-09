package com.lavkesh.cloud.securityService.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("test-service")
public interface TestService {

  @RequestMapping(
    value = "/applicationName",
    method = RequestMethod.GET,
    consumes = "application/json"
  )
  String getApplicationName();
}
