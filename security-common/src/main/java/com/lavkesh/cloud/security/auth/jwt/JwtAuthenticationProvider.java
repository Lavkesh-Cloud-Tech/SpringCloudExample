package com.lavkesh.cloud.security.auth.jwt;

import com.lavkesh.cloud.security.config.AuthenticationConfig;
import com.lavkesh.cloud.security.model.RawAccessJwtToken;
import com.lavkesh.cloud.security.model.UserContext;
import com.lavkesh.cloud.security.util.CommonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

public class JwtAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private AuthenticationConfig authenticationConfig;

  private Key publicKey;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

    Key publicKey = getPublicKey();
    Assert.notNull(publicKey, "Public key is empty.");

    Jws<Claims> jwsClaims = rawAccessToken.parseClaims(publicKey);

    UserContext context = createUserContext(jwsClaims);
    context.setJwtToken(rawAccessToken);

    return new JwtAuthenticationToken(context, context.getAuthorities());
  }

  protected UserContext createUserContext(Jws<Claims> jwsClaims) {
    Claims body = jwsClaims.getBody();
    String subject = body.getSubject();
    List<String> scopes = body.get("scopes", List.class);
    List<GrantedAuthority> authorities =
        scopes
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority))
            .collect(Collectors.toList());

    return UserContext.create(subject, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }

  public Key getPublicKey() {
    if (publicKey == null) {
      String publicKeyPath = authenticationConfig.getPublicKeyPath();
      publicKey = CommonUtils.getPublicKey(publicKeyPath);
    }
    return publicKey;
  }
}
