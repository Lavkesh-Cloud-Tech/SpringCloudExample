package com.lavkesh.cloud.security.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;

public class CommonUtils {

  protected static Log logger = LogFactory.getLog(CommonUtils.class);
  protected static final String HEADER_PREFIX = "Bearer ";

  public static String extract(String header) {
    if (StringUtils.isBlank(header)) {
      throw new AuthenticationServiceException("Authorization header cannot be blank!");
    }

    if (header.length() < HEADER_PREFIX.length()) {
      throw new AuthenticationServiceException("Invalid authorization header size.");
    }

    return header.substring(HEADER_PREFIX.length(), header.length());
  }

  public static Date getCurrentTimeInUtc() {
    Instant ins = Instant.now();
    Date currentTime = Date.from(ins);
    return currentTime;
  }

  public static Date getCurrentTimeWithOffsetInUtc(Integer Offset, ChronoUnit unit) {
    Instant ins = Instant.now();
    Instant time = ins.plus(Offset, unit);
    Date date = Date.from(time);
    return date;
  }

  public static Key getPrivateKey(String privateKeyPath) throws RuntimeException {
    try {
      Path path = Paths.get(ClassLoader.getSystemResource(privateKeyPath).toURI());
      byte[] keyBytes = Files.readAllBytes(path);

      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(spec);
    } catch (URISyntaxException
        | IOException
        | NoSuchAlgorithmException
        | InvalidKeySpecException e) {
      logger.error("Error in JwtSettings.getPrivateKey", e);
      throw new RuntimeException(e);
    }
  }

  public static Key getPublicKey(String publicKeyPath) throws RuntimeException {
    try {
      Path path = Paths.get(ClassLoader.getSystemResource(publicKeyPath).toURI());
      byte[] keyBytes = Files.readAllBytes(path);

      X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(spec);
    } catch (URISyntaxException
        | IOException
        | NoSuchAlgorithmException
        | InvalidKeySpecException e) {
      logger.error("Error in JwtSettings.getPublicKey", e);
      throw new RuntimeException(e);
    }
  }
}
