package com.lavkesh.cloud.security.auth;

import com.lavkesh.cloud.security.config.AuthenticationConfig;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(AnonymousRequestMatcher.class)
public class AnonymousRequestMatcher implements RequestMatcher {

  private static final String DEFAULT_ANONYMOUS_FILTER = "/DEFAULT_ANONYMOUS_FILTER/**";

  @Autowired
  private AuthenticationConfig authenticationConfig;
  private OrRequestMatcher matchers;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    List<RequestMatcher> matcher = getRequestMatcher(authenticationConfig);
    matchers = new OrRequestMatcher(matcher);
  }

  public static final List<RequestMatcher> getRequestMatcher(
      AuthenticationConfig authenticationConfig) {
    List<String> anonymousPath = authenticationConfig.getAnonymousPath();
    List<RequestMatcher> matcher =
        anonymousPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());

    if (anonymousPath.size() == 0) {
      matcher.add(new AntPathRequestMatcher(DEFAULT_ANONYMOUS_FILTER));
    }
    return matcher;
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    return matchers.matches(request);
  }
}
