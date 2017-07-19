package com.lavkesh.cloud.security.auth.jwt;

import com.lavkesh.cloud.security.config.JwtSettings;
import com.lavkesh.cloud.security.model.JwtToken;
import com.lavkesh.cloud.security.model.RawAccessJwtToken;
import com.lavkesh.cloud.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@ConditionalOnMissingBean(JwtAuthenticationProvider.class)
public class JwtAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private JwtSettings jwtSettings;

  public JwtAuthenticationProvider() {
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

    Key publicKey = jwtSettings.getPublicKey();
    Assert.notNull(publicKey, "Public key is empty.");

    Jws<Claims> jwsClaims = rawAccessToken.parseClaims(publicKey);

    UserContext context = createUserContext(jwsClaims);

    return new JwtAuthenticationToken(context, context.getAuthorities());
  }

  protected UserContext createUserContext(Jws<Claims> jwsClaims){
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
}
