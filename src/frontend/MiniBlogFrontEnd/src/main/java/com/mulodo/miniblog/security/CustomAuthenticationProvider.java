package com.mulodo.miniblog.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

@SuppressWarnings("deprecation")
@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		String access_key = authentication.getName();
        
        if (access_key != null)
        {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new GrantedAuthorityImpl("ROLE_USER"));
            
            Authentication auth = new UsernamePasswordAuthenticationToken(access_key, null, grantedAuths);
            return auth;
        } else {
            return null;
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
