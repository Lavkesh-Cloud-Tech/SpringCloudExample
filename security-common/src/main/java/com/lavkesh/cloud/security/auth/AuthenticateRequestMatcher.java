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
@ConditionalOnMissingBean(AuthenticateRequestMatcher.class)
public class AuthenticateRequestMatcher implements RequestMatcher {

  private static final String DEFAULT_AUTHENTICATION_FILTER = "/**";

  @Autowired private AuthenticationConfig authenticationConfig;

  private OrRequestMatcher matchers;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    List<String> authenticationPath = authenticationConfig.getAuthenticationPath();
    List<RequestMatcher> reqMatcher =
        authenticationPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());
    if(authenticationPath.size() == 0){
      reqMatcher.add(new AntPathRequestMatcher(DEFAULT_AUTHENTICATION_FILTER));
    }
    matchers = new OrRequestMatcher(reqMatcher);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    return matchers.matches(request);
  }
}
