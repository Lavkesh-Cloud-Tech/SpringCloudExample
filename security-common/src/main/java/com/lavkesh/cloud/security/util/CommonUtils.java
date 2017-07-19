package com.lavkesh.cloud.security.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;

public interface CommonUtils {

  String HEADER_PREFIX = "Bearer ";

  static String extract(String header) {
    if (StringUtils.isBlank(header)) {
      throw new AuthenticationServiceException("Authorization header cannot be blank!");
    }

    if (header.length() < HEADER_PREFIX.length()) {
      throw new AuthenticationServiceException("Invalid authorization header size.");
    }

    return header.substring(HEADER_PREFIX.length(), header.length());
  }

  static Date getCurrentTimeInUtc() {
    Instant ins = Instant.now();
    Date currentTime = Date.from(ins);
    return currentTime;
  }

  static Date getCurrentTimeWithOffsetInUtc(Integer Offset, ChronoUnit unit) {
    Instant ins = Instant.now();
    Instant time = ins.plus(Offset, unit);
    Date date = Date.from(time);
    return date;
  }
}
