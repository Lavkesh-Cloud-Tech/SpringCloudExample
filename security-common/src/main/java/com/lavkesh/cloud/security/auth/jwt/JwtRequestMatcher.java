package com.lavkesh.cloud.security.auth.jwt;

import com.lavkesh.cloud.security.auth.AnonymousRequestMatcher;
import com.lavkesh.cloud.security.auth.AuthenticateRequestMatcher;
import com.lavkesh.cloud.security.config.AuthenticationConfig;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(JwtRequestMatcher.class)
public class JwtRequestMatcher implements RequestMatcher {

  @Autowired
  private AuthenticationConfig authenticationConfig;

  private RequestMatcher anonymousMatchers;
  private RequestMatcher processingMatcher;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    List<RequestMatcher> m = AnonymousRequestMatcher.getRequestMatcher(authenticationConfig);
    anonymousMatchers = new OrRequestMatcher(m);

    m = AuthenticateRequestMatcher.getRequestMatcher(authenticationConfig);
    processingMatcher = new OrRequestMatcher(m);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    boolean enableJwtAuthentication = authenticationConfig.isEnableJwtAuthentication();
    if (!enableJwtAuthentication) {
      return false;
    }

    if (anonymousMatchers.matches(request)) {
      return false;
    }

    return processingMatcher.matches(request) ? true : false;
  }
}
