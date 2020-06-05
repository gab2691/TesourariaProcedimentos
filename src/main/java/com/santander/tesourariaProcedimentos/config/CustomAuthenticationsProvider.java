package com.santander.tesourariaProcedimentos.config;

import com.santander.tesourariaProcedimentos.entities.Analista;
import com.santander.tesourariaProcedimentos.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CustomAuthenticationsProvider implements AuthenticationProvider {
    
    @Autowired
    private UserAuthenticationService userAuthenticationService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        String userAndPassword = Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
        
        try {
            Analista userDetails = (Analista) userAuthenticationService.loadUserByUsername(userAndPassword);
            return new UsernamePasswordAuthenticationToken(userName,password, userDetails.getAuthorities());
        }catch (Exception ex){
        
        }
        return null;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    
}
