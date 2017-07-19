package com.lavkesh.cloud.security.config;

import com.lavkesh.cloud.security.model.JwtToken;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lavkesh.security.jwt")
public class JwtSettings {

  protected final Log logger = LogFactory.getLog(getClass());

  /** Token issuer. */
  private String tokenIssuer;

  /** {@link JwtToken} will expire after this time. */
  private Integer tokenExpirationTime;

  /** {@link JwtToken} can be refreshed during this timeframe. */
  private Integer refreshTokenExpTime;

  private String privateKeyPath;
  private String publicKeyPath;

  private Key privateKey;
  private Key publicKey;

  public String getTokenIssuer() {
    return tokenIssuer;
  }

  public void setTokenIssuer(String tokenIssuer) {
    this.tokenIssuer = tokenIssuer;
  }

  public Integer getRefreshTokenExpTime() {
    return refreshTokenExpTime;
  }

  public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
    this.refreshTokenExpTime = refreshTokenExpTime;
  }

  public Integer getTokenExpirationTime() {
    return tokenExpirationTime;
  }

  public void setTokenExpirationTime(Integer tokenExpirationTime) {
    this.tokenExpirationTime = tokenExpirationTime;
  }

  public String getPrivateKeyPath() {
    return privateKeyPath;
  }

  public void setPrivateKeyPath(String privateKeyPath) {
    this.privateKeyPath = privateKeyPath;
  }

  public String getPublicKeyPath() {
    return publicKeyPath;
  }

  public void setPublicKeyPath(String publicKeyPath) {
    this.publicKeyPath = publicKeyPath;
  }

  public Key getPublicKey() {
    if (publicKey == null) {
      try {
        Path path = Paths.get(ClassLoader.getSystemResource(publicKeyPath).toURI());
        byte[] keyBytes = Files.readAllBytes(path);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publicKey = kf.generatePublic(spec);
      } catch (URISyntaxException
          | IOException
          | NoSuchAlgorithmException
          | InvalidKeySpecException e) {
        logger.error("Error in JwtSettings.getPublicKey", e);
      }
    }
    return publicKey;
  }

  public Key getPrivateKey() {
    if (privateKey == null) {
      try {
        Path path = Paths.get(ClassLoader.getSystemResource(privateKeyPath).toURI());
        byte[] keyBytes = Files.readAllBytes(path);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        privateKey = kf.generatePrivate(spec);
      } catch (URISyntaxException
          | IOException
          | NoSuchAlgorithmException
          | InvalidKeySpecException e) {
        logger.error("Error in getting private key: ", e);
      }
    }
    return privateKey;
  }
}