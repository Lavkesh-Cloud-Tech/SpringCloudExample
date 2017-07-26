package com.lavkesh.cloud.security.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lavkesh.security")
public class AuthenticationConfig {

  private boolean enableJwtAuthentication = true;
  private List<String> anonymousPath = new ArrayList<>();
  private List<String> authenticationPath = new ArrayList<>();
  private String publicKeyPath;

  public boolean isEnableJwtAuthentication() {
    return enableJwtAuthentication;
  }

  public void setEnableJwtAuthentication(boolean enableJwtAuthentication) {
    this.enableJwtAuthentication = enableJwtAuthentication;
  }

  public List<String> getAnonymousPath() {
    return anonymousPath;
  }

  public void setAnonymousPath(List<String> anonymousPath) {
    this.anonymousPath = anonymousPath;
  }

  public List<String> getAuthenticationPath() {
    return authenticationPath;
  }

  public void setAuthenticationPath(List<String> authenticationPath) {
    this.authenticationPath = authenticationPath;
  }

  public String getPublicKeyPath() {
    return publicKeyPath;
  }

  public void setPublicKeyPath(String publicKeyPath) {
    this.publicKeyPath = publicKeyPath;
  }
}