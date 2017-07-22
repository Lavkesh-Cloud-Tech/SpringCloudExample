package com.lavkesh.cloud.securityService.security.model;

import com.lavkesh.cloud.security.model.JwtToken;
import com.lavkesh.cloud.security.model.Scopes;
import com.lavkesh.cloud.security.model.UserContext;
import com.lavkesh.cloud.security.util.CommonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Created by RATHIL on 7/19/2017.
 */
@Component
public class JwtTokenFactory {

  protected final Log logger = LogFactory.getLog(getClass());

  @Autowired
  private AjaxAuthenticationConfig settings;
  private Key privateKey;

  public JwtToken createAccessJwtToken(UserContext userContext) {
    if (StringUtils.isBlank(userContext.getUsername())) {
      throw new IllegalArgumentException("Cannot create JWT Token without username");
    }

    Collection<GrantedAuthority> authorities = userContext.getAuthorities();
    if (CollectionUtils.isEmpty(authorities)) {
      throw new IllegalArgumentException("User doesn't have any privileges");
    }

    Claims claims = Jwts.claims();
    claims.setSubject(userContext.getUsername());

    List<String> userScopes =
        authorities.stream().map(s -> s.toString()).collect(Collectors.toList());
    claims.put("scopes", userScopes);

    Date currentTime = CommonUtils.getCurrentTimeInUtc();

    Integer Offset = settings.getTokenExpirationTime();
    Date expirationDate = CommonUtils.getCurrentTimeWithOffsetInUtc(Offset, ChronoUnit.MINUTES);

    Key privateKey = getPrivateKey();

    String token =
        Jwts.builder()
            .setClaims(claims)
            .setIssuer(settings.getTokenIssuer())
            .setIssuedAt(currentTime)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact();

    return new AccessJwtToken(token, claims);
  }

  public JwtToken createRefreshToken(UserContext userContext) {
    if (StringUtils.isBlank(userContext.getUsername())) {
      throw new IllegalArgumentException("Cannot create JWT Token without username");
    }

    Claims claims = Jwts.claims().setSubject(userContext.getUsername());
    claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

    Date currentTime = CommonUtils.getCurrentTimeInUtc();

    Integer Offset = settings.getRefreshTokenExpTime();
    Date refreshExpDate = CommonUtils.getCurrentTimeWithOffsetInUtc(Offset, ChronoUnit.MINUTES);

    Key privateKey = getPrivateKey();

    String token =
        Jwts.builder()
            .setClaims(claims)
            .setIssuer(settings.getTokenIssuer())
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(currentTime)
            .setExpiration(refreshExpDate)
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact();

    return new AccessJwtToken(token, claims);
  }

  public Key getPrivateKey() {
    if (privateKey == null) {
      String privateKeyPath = settings.getPrivateKeyPath();
      privateKey = CommonUtils.getPrivateKey(privateKeyPath);
    }
    return privateKey;
  }
}
