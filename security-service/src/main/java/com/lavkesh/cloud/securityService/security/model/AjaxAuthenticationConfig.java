package com.lavkesh.cloud.securityService.security.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lavkesh.security.ajax")
public class AjaxAuthenticationConfig {

  private boolean enable = true;
  private String tokenIssuer;
  private Integer tokenExpirationTime;
  private Integer refreshTokenExpTime;
  private String privateKeyPath;
  private List<String> authenticationPath = new ArrayList();

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public String getTokenIssuer() {
    return tokenIssuer;
  }

  public void setTokenIssuer(String tokenIssuer) {
    this.tokenIssuer = tokenIssuer;
  }

  public Integer getTokenExpirationTime() {
    return tokenExpirationTime;
  }

  public void setTokenExpirationTime(Integer tokenExpirationTime) {
    this.tokenExpirationTime = tokenExpirationTime;
  }

  public Integer getRefreshTokenExpTime() {
    return refreshTokenExpTime;
  }

  public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
    this.refreshTokenExpTime = refreshTokenExpTime;
  }

  public List<String> getAuthenticationPath() {
    return authenticationPath;
  }

  public void setAuthenticationPath(List<String> authenticationPath) {
    this.authenticationPath = authenticationPath;
  }

  public String getPrivateKeyPath() {
    return privateKeyPath;
  }

  public void setPrivateKeyPath(String privateKeyPath) {
    this.privateKeyPath = privateKeyPath;
  }

}
