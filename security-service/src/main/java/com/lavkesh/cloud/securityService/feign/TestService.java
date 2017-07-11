package com.lavkesh.cloud.securityService.feign;

import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "test-service", path = "/test-service")
public interface TestService {

  @GetMapping(
    value = "/applicationName",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, String> getApplicationName();
}
