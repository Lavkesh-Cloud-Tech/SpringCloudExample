package com.lavkesh.cloud.security.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "lavkesh.security")
@Component
@RefreshScope
public class AuthenticationConfig {

  private List<String> jwtPathToSkip = new ArrayList<>();
  private List<String> jwtAuthenticationPath = new ArrayList<>();
  private List<String> ajaxAuthenticationPath = new ArrayList<>();
  private boolean enableAjaxAuthentication = true;

  public List<String> getJwtPathToSkip() {
    return jwtPathToSkip;
  }

  public void setJwtPathToSkip(List<String> jwtPathToSkip) {
    this.jwtPathToSkip = jwtPathToSkip;
  }

  public List<String> getJwtAuthenticationPath() {
    return jwtAuthenticationPath;
  }

  public void setJwtAuthenticationPath(List<String> jwtAuthenticationPath) {
    this.jwtAuthenticationPath = jwtAuthenticationPath;
  }

  public List<String> getAjaxAuthenticationPath() {
    return ajaxAuthenticationPath;
  }

  public void setAjaxAuthenticationPath(List<String> ajaxAuthenticationPath) {
    this.ajaxAuthenticationPath = ajaxAuthenticationPath;
  }

  public boolean isEnableAjaxAuthentication() {
    return enableAjaxAuthentication;
  }

  public void setEnableAjaxAuthentication(boolean enableAjaxAuthentication) {
    this.enableAjaxAuthentication = enableAjaxAuthentication;
  }
}
