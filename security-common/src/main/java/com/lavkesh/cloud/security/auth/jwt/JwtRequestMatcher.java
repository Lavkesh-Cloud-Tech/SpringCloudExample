package com.lavkesh.cloud.security.auth.jwt;

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
@ConditionalOnMissingBean(JwtRequestMatcher.class)
public class JwtRequestMatcher implements RequestMatcher {

  private static final String DEFAULT_JWT_AUTHENTICATION_FILTER = "/**";

  @Autowired
  private AuthenticationConfig authenticationConfig;

  private RequestMatcher anonymousMatchers;
  private RequestMatcher processingMatcher;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    List<String> anonymousPath = authenticationConfig.getAnonymousPath();
    List<RequestMatcher> m =
        anonymousPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());
    anonymousMatchers = new OrRequestMatcher(m);

    List<String> jwtAuthenticationPath = authenticationConfig.getAuthenticationPath();
    List<RequestMatcher> pM =
        jwtAuthenticationPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());
    if (pM.size() == 0) {
      pM.add(new AntPathRequestMatcher(DEFAULT_JWT_AUTHENTICATION_FILTER));
    }
    processingMatcher = new OrRequestMatcher(pM);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    boolean enableJwtAuthentication = authenticationConfig.isEnableJwtAuthentication();
    if (!enableJwtAuthentication) {
      return false;
    }

    if(anonymousMatchers.matches(request)){
      return false;
    }

    return processingMatcher.matches(request) ? true : false;
  }
}
