package com.santander.tesourariaProcedimentos.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.santander.tesourariaProcedimentos.customDeserializer.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "T_SUST_PROCEDIMENTO_S")
public class Procedimentos {
    
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPK_T_SUST_PROCEDIMENTOS_S")
    @SequenceGenerator(name = "SPK_T_SUST_PROCEDIMENTOS_S",sequenceName = "SPK_T_SUST_PROCEDIMENTOS_S",allocationSize = 1)
    private Integer id;
    
    @Column(name = "DS_RESU_PROCEDIMENTO")
    @JsonDeserialize(using = JacksonConfigurationString.class)
    private String descProcedimento;
    
    @Column(name = "DT_PROCEDIMENTO",nullable = false, updatable= false)
    @JsonDeserialize(using = JacksonConfigurationString.class)
    private String dataProcedimento;
    
    @Column(name = "CD_TICKET")
    @JsonDeserialize(using = JacksonConfigurationString.class)
    private String ticket;
    
   
    @OneToOne(cascade=CascadeType.ALL)
    @JsonDeserialize(using = JacksonDeserializerSistemas.class)
    @JoinColumn(name = "FK_SISTEMAS", referencedColumnName = "PK")
    private Sistemas sistemas;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JsonDeserialize(using = JacksonDeserializerMalhaBatch.class)
    @JoinColumn(name = "FK_MALHA_BATCH", referencedColumnName = "PK")
    private MalhaBatch malhaBatch;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JsonDeserialize(using = JacksonDeserializerPalavraChave.class)
    @JoinColumn(name = "FK_CHAVE", referencedColumnName = "PK")
    private PalavraChave palavraChave;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "FK_ANALISTA", referencedColumnName = "PK")
    private Analista analista;
    
    @OneToMany(mappedBy = "procedimentos",cascade = {CascadeType.ALL})
    private List<Documentos> documentos;
    
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL}, mappedBy = "procedimentos")
    private List<Imagem> imagem;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDescProcedimento() {
        return descProcedimento;
    }
    
    public void setDescProcedimento(String descProcedimento) {
        this.descProcedimento = descProcedimento;
    }
    
    public String getDataProcedimento() {
        return this.dataProcedimento;
    }
    
    public void setDataProcedimento(String dataProcedimento){
        this.dataProcedimento = dataProcedimento;
    }
    
    public String getTicket() {
        return ticket;
    }
    
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
    public Sistemas getSistemas() {
        return sistemas;
    }
    
    public void setSistemas(Sistemas sistemas) {
        this.sistemas = sistemas;
    }
    
    public MalhaBatch getMalhaBatch() {
        return malhaBatch;
    }
    
    public void setMalhaBatch(MalhaBatch malhaBatch) {
        this.malhaBatch = malhaBatch;
    }
    
    public PalavraChave getPalavraChave() {
        return palavraChave;
    }
    
    public void setPalavraChave(PalavraChave palavraChave) {
        this.palavraChave = palavraChave;
    }
    
    public Analista getAnalista() {
        return analista;
    }
    
    public void setAnalista(Analista analista) {
        this.analista = analista;
    }
    
    public List<Documentos> getDocumentos() {
        return documentos;
    }
    
    public void setDocumentos(List<Documentos> documentos) {
        this.documentos = documentos;
    }
    
    public List<Imagem> getImagem() {
        return imagem;
    }
    
    public void setImagem(List<Imagem> imagem) {
        this.imagem = imagem;
    }
    
    public String oracleData(String data) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(data));
    }
    
    public String formatStringToDate(String data) throws ParseException {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "Procedimentos{" +
                       "id=" + id +
                       ", descProcedimento='" + descProcedimento + '\'' +
                       ", dataProcedimento='" + dataProcedimento + '\'' +
                       ", ticket='" + ticket + '\'' +
                       ", sistemas=" + sistemas +
                       ", malhaBatch=" + malhaBatch +
                       ", palavraChave=" + palavraChave +
                       ", analista=" + analista.getUser() +
                       ", documentos=" + documentos +
                       ", imagem=" + imagem +
                       '}';
    }
}
