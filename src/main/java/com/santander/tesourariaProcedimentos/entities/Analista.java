package com.santander.tesourariaProcedimentos.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "T_SUST_ANALISTA_S")
public class Analista implements UserDetails {
    
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPK_T_SUST_GERAL_S")
    @SequenceGenerator(name = "SPK_T_SUST_GERAL_S",sequenceName = "SPK_T_SUST_GERAL_S",allocationSize = 1)
    private int id;
    
    @Column(name = "NM_MATRICULA")
    private String user;
    
    @Column(name = "DS_ANALISTA")
    private String nome;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUser() {
        return user;
    }
    
    public Analista setUser(String user) {
        this.user = user;
        return this;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<String> roles  = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("user");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (String r : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(r));
        }
        return grantedAuthorities;
    }
    
    @Override
    public String getPassword() {
        return null;
    }
    
    @Override
    public String getUsername() {
        return this.nome;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
}
