package com.lavkesh.cloud.securityService.controller;

import com.lavkesh.cloud.securityService.feign.TestService;
import com.lavkesh.cloud.securityService.modal.LoginForm;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class AuthenticationController {

  @Value("${application.username}")
  private String username;

  @Value("${application.password}")
  private String password;

  @Autowired private TestService testService;

  @GetMapping(
    value = "/usernameAndPassword",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, String> getUsernameAndPassword() {
    Map<String, String> map = new HashedMap();
    map.put("username", username);
    map.put("password", password);
    return map;
  }

  @PostMapping(
    value = "/authenticate",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Boolean> authenticate(@RequestBody LoginForm loginForm) {
    boolean authenticate = false;
    if (username.equals(loginForm.getUsername()) && password.equals(loginForm.getPassword())) {
      authenticate = true;
    }

    Map<String, Boolean> map = new HashedMap();
    map.put("authenticate", authenticate);
    return map;
  }

  @GetMapping(
    value = "/applicationName",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, String> getApplicationName() {
    return testService.getApplicationName();
  }
}
