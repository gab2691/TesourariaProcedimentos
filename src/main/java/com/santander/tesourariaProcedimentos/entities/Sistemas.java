package com.santander.tesourariaProcedimentos.entities;

import javax.persistence.*;

@Entity
@Table(name = "T_SUST_SISTEMAS_S")
public class Sistemas {
    
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPK_T_SUST_GERAL_S")
    @SequenceGenerator(name = "SPK_T_SUST_GERAL_S",sequenceName = "SPK_T_SUST_GERAL_S",allocationSize = 1)
    private int id;
    
    public Sistemas(String sigla) {
        this.sigla = sigla;
    }
    
    public Sistemas(){
    
    }
    
    @Column(name = "SG_SISTEMA")
    private String sigla;
    
    @Column(name = "NM_SISTEMA")
    private String nomeSistema;
    
  
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    public String getNomeSistema() {
        return nomeSistema;
    }
    
    public void setNomeSistema(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }
}
