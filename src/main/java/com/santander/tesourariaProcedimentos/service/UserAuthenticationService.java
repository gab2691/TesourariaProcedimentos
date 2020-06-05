package com.santander.tesourariaProcedimentos.service;

import com.santander.tesourariaProcedimentos.config.SSLDesable;
import com.santander.tesourariaProcedimentos.entities.Analista;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class UserAuthenticationService implements UserDetailsService {
    
    private SSLDesable sslDesable = new SSLDesable();
    
    private HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    
    private HttpHeaders headers = new HttpHeaders();
    
    
   
    @Override
    public UserDetails loadUserByUsername(String userAndPassword) throws UsernameNotFoundException {
        requestFactory.setHttpClient(sslDesable.desableSsl());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
    
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Basic " + userAndPassword + "");
        String url = "https://ssohub-prd.bs.br.bsch/sso/authenticate/internal-basic?gw-app-key=3ef59550c5f401341b9b005056906329";
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        
        try {
            ResponseEntity<Analista> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, Analista.class);
            return new Analista();
        }catch (Exception ex){
        
        }
        return null;
    }
}
