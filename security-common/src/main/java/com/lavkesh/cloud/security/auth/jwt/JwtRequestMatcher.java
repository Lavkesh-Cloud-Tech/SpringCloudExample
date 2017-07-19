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

  @Autowired
  private AuthenticationConfig authenticationConfig;

  private OrRequestMatcher matchers;

  private RequestMatcher processingMatcher;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    List<String> jwtPathToSkip = authenticationConfig.getJwtPathToSkip();
    List<RequestMatcher> m =
        jwtPathToSkip
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());
    matchers = new OrRequestMatcher(m);

    List<String> jwtAuthenticationPath = authenticationConfig.getJwtAuthenticationPath();
    List<RequestMatcher> pM =
        jwtAuthenticationPath
            .stream()
            .map(path -> new AntPathRequestMatcher(path))
            .collect(Collectors.toList());
    processingMatcher = new OrRequestMatcher(pM);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    if (matchers.matches(request)) {
      return false;
    }
    return processingMatcher.matches(request) ? true : false;
  }
}
