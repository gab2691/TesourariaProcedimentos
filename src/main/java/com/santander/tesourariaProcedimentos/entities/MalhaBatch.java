package com.santander.tesourariaProcedimentos.entities;


import javax.persistence.*;

@Entity
@Table(name = "T_SUST_MALHA_BATCH_S")
public class MalhaBatch {
    
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPK_T_SUST_GERAL_S")
    @SequenceGenerator(name = "SPK_T_SUST_GERAL_S",sequenceName = "SPK_T_SUST_GERAL_S",allocationSize = 1)
    private int id;
    
    @Column(name = "NM_JOB")
    private String nomeJob;
    
    public MalhaBatch(String job) {
        this.nomeJob = job;
    }
    
    public MalhaBatch(){
    
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNomeJob() {
        return nomeJob;
    }
    
    public void setNomeJob(String nomeJob) {
        this.nomeJob = nomeJob;
    }
}
