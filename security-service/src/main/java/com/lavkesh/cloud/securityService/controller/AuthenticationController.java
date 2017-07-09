package com.lavkesh.cloud.securityService.controller;

import com.lavkesh.cloud.securityService.feign.TestService;
import com.lavkesh.cloud.securityService.modal.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class AuthenticationController {

  @Value("${application.username}")
  private String username;

  @Value("${application.password}")
  private String password;

  @Autowired private TestService testService;

  @RequestMapping("/usernameAndPassword")
  public String getUsernameAndPassword() {
    return "{username : " + username + ", passowrd : " + password + "}";
  }

  @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
  public boolean authenticate(@RequestBody LoginForm loginForm) {

    if (username.equals(loginForm.getUsername()) && password.equals(loginForm.getPassword())) {
      return true;
    }

    return false;
  }

  @RequestMapping("/applicationName")
  public String getApplicationName() {
    return testService.getApplicationName();
  }
}
