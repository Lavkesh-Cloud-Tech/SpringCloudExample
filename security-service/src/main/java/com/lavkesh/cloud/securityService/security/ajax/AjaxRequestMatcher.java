package com.lavkesh.cloud.securityService.security.ajax;

import com.lavkesh.cloud.security.config.AuthenticationConfig;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AjaxRequestMatcher implements RequestMatcher {

  private AuthenticationConfig authenticationConfig;
  private OrRequestMatcher matchers;

  @Autowired
  public AjaxRequestMatcher(AuthenticationConfig authenticationConfig) {
    Assert.notNull(authenticationConfig, "Authentication Config is null.");
    this.authenticationConfig = authenticationConfig;
  }

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    List<String> ajaxAuthenticationPath = authenticationConfig.getAjaxAuthenticationPath();
    List<RequestMatcher> m =
        ajaxAuthenticationPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());
    matchers = new OrRequestMatcher(m);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    return matchers.matches(request);
  }
}
