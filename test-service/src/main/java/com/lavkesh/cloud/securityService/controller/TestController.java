package com.lavkesh.cloud.securityService.controller;

import java.util.Map;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

  @Autowired Environment env;

  @Value("${lavkesh.application.name}")
  private String applicationName;

  @GetMapping(value = "/applicationName")
  Map<String, String> getApplicationName() {
    Map<String, String> map = new HashedMap();
    map.put("applicationName", applicationName);
    return map;
  }
}
