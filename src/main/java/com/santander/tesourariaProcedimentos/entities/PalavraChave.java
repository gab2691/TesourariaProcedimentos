package com.santander.tesourariaProcedimentos.entities;

import javax.persistence.*;

@Entity
@Table(name = "T_SUST_CHAVE_S")
public class PalavraChave {
    
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPK_T_SUST_GERAL_S")
    @SequenceGenerator(name = "SPK_T_SUST_GERAL_S",sequenceName = "SPK_T_SUST_GERAL_S",allocationSize = 1)
    private int id;
    
    @Column(name = "CD_CHAVE")
    private String chave;
    
    public PalavraChave(String chave) {
        this.chave = chave;
    }
    
    public PalavraChave(){
    
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getChave() {
        return chave;
    }
    
    public void setChave(String chave) {
        this.chave = chave;
    }
}
