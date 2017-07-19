package com.lavkesh.cloud.security.model;

import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

public class UserContext {
  private final String username;
  private final Collection<GrantedAuthority> authorities;

  private UserContext(String username, Collection<GrantedAuthority> authorities) {
    this.username = username;
    this.authorities = authorities;
  }

  public static UserContext create(String username, Collection<GrantedAuthority> authorities) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException("Username is blank: " + username);
    return new UserContext(username, authorities);
  }

  public String getUsername() {
    return username;
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }
}
