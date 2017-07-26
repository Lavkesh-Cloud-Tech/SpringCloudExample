package com.lavkesh.cloud.securityService.security.ajax;

import com.lavkesh.cloud.securityService.security.model.AjaxAuthenticationConfig;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AjaxRequestMatcher implements RequestMatcher {

  private AjaxAuthenticationConfig ajaxAuthenticationConfig;
  private RequestMatcher matchers;

  @Autowired
  public AjaxRequestMatcher(AjaxAuthenticationConfig ajaxAuthenticationConfig) {
    Assert.notNull(ajaxAuthenticationConfig, "Authentication Config is null.");
    this.ajaxAuthenticationConfig = ajaxAuthenticationConfig;
  }

  @PostConstruct
  public void init() {
    List<String> authenticationPath = ajaxAuthenticationConfig.getAuthenticationPath();
    List<RequestMatcher> m =
        authenticationPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());
    matchers = new OrRequestMatcher(m);
  }

  @EventListener
  public void handleContextRefresh(RefreshScopeRefreshedEvent event) {
    init();
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    boolean enable = ajaxAuthenticationConfig.isEnable();
    if (!enable) {
      return false;
    }

    return matchers.matches(request);
  }
}
