package com.lavkesh.cloud.securityService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

  @Autowired Environment env;

  @RequestMapping("/applicationName")
  String getApplicationName() {
    String applicationName = env.getProperty("lavkesh.application.name");
    return "{applicationName: " + applicationName + "}";
  }
}
