package com.lavkesh.cloud.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavkesh.cloud.security.exceptions.AuthMethodNotSupportedException;
import com.lavkesh.cloud.security.exceptions.JwtExpiredTokenException;
import com.lavkesh.cloud.security.model.ErrorCode;
import com.lavkesh.cloud.security.model.ErrorResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Autowired
  private ObjectMapper mapper;

  public CustomAuthenticationFailureHandler() {
  }

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {

    PrintWriter resWriter = response.getWriter();

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    if (e instanceof BadCredentialsException) {
      ErrorResponse errorResponse =
          ErrorResponse.unauthorized("Invalid username or password", ErrorCode.AUTHENTICATION);
      mapper.writeValue(resWriter, errorResponse);
      return;
    } else if (e instanceof JwtExpiredTokenException) {
      ErrorResponse errorResponse =
          ErrorResponse.unauthorized("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED);
      mapper.writeValue(resWriter, errorResponse);
      return;
    } else if (e instanceof AuthMethodNotSupportedException) {
      ErrorResponse errorResponse =
          ErrorResponse.unauthorized(e.getMessage(), ErrorCode.AUTHENTICATION);
      mapper.writeValue(resWriter, errorResponse);
      return;
    }

    ErrorResponse errorResponse =
        ErrorResponse.unauthorized("Authentication failed", ErrorCode.AUTHENTICATION);
    mapper.writeValue(resWriter, errorResponse);
  }
}
