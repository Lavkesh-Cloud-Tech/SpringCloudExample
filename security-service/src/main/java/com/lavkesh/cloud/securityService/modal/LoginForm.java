package com.lavkesh.cloud.securityService.modal;

import javax.validation.constraints.NotNull;

public class LoginForm {

  @NotNull public String username;

  @NotNull public String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
