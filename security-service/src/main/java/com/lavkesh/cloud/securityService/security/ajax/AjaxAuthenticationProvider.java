package com.lavkesh.cloud.securityService.security.ajax;

import com.lavkesh.cloud.security.model.UserContext;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    public AjaxAuthenticationProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String username =  StringUtils.trimToEmpty((String) authentication.getPrincipal());
        String password = StringUtils.trimToEmpty((String) authentication.getCredentials());

        User user = null;
        if(username.equals("user")) {
            if(password.equals("password")){
                List<GrantedAuthority> authorities =  new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                user = new User(username, "", authorities);
            } else{
                throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
            }
        } else if(username.equals("admin")) {
            if(password.equals("admin")){
                List<GrantedAuthority> authorities =  new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                user = new User(username, null, authorities);
            } else{
                throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
            }
        }

        if(user == null){
            throw new UsernameNotFoundException("User not found: " + username);
        }

        UserContext userContext = UserContext.create(user.getUsername(), user.getAuthorities());

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
