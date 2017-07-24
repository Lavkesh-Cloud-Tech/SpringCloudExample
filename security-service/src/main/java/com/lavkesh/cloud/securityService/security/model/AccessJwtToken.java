package com.lavkesh.cloud.securityService.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lavkesh.cloud.security.model.JwtToken;
import io.jsonwebtoken.Claims;

/** Raw representation of JWT Token. */
public final class AccessJwtToken implements JwtToken {

  private final String rawToken;
  @JsonIgnore private Claims claims;

  protected AccessJwtToken(final String token, Claims claims) {
    this.rawToken = token;
    this.claims = claims;
  }

  public String getToken() {
    return this.rawToken;
  }

  public Claims getClaims() {
    return claims;
  }
}