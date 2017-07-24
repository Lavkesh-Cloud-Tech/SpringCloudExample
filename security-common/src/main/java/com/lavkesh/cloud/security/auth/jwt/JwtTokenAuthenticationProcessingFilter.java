package com.lavkesh.cloud.security.auth.jwt;

import com.lavkesh.cloud.security.model.RawAccessJwtToken;
import com.lavkesh.cloud.security.util.CommonUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

  public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";

  private final AuthenticationFailureHandler failureHandler;

  @Autowired
  public JwtTokenAuthenticationProcessingFilter(
      AuthenticationFailureHandler failureHandler, RequestMatcher matcher) {
    super(matcher);
    this.failureHandler = failureHandler;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    String tokenPayload = request.getHeader(JWT_TOKEN_HEADER_PARAM);
    String rawToken = CommonUtils.extract(tokenPayload);
    RawAccessJwtToken token = new RawAccessJwtToken(rawToken);
    return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {

    SecurityContextHolder.getContext().setAuthentication(authResult);
    chain.doFilter(request, response);

  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    SecurityContextHolder.clearContext();
    failureHandler.onAuthenticationFailure(request, response, failed);
  }
}