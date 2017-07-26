package com.lavkesh.cloud.security.auth;

import com.lavkesh.cloud.security.config.AuthenticationConfig;
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

public class AuthenticateRequestMatcher implements RequestMatcher {

  private static final String DEFAULT_AUTHENTICATION_FILTER = "/**";

  @Autowired
  private AuthenticationConfig authenticationConfig;

  private OrRequestMatcher matchers;

  @PostConstruct
  public void init() {
    List<RequestMatcher> matcher = getRequestMatcher(authenticationConfig);
    matchers = new OrRequestMatcher(matcher);
  }

  @EventListener
  public void handleContextRefresh(RefreshScopeRefreshedEvent event) {
    init();
  }

  public static final List<RequestMatcher> getRequestMatcher(
      AuthenticationConfig authenticationConfig) {
    List<String> anonymousPath = authenticationConfig.getAuthenticationPath();
    List<RequestMatcher> matcher =
        anonymousPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());

    if (anonymousPath.size() == 0) {
      matcher.add(new AntPathRequestMatcher(DEFAULT_AUTHENTICATION_FILTER));
    }
    return matcher;
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    return matchers.matches(request);
  }
}
