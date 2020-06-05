package com.santander.tesourariaProcedimentos.entities;


import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.Cascade;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_SUST_DOCS_S")
public class Documentos  {
    
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPK_T_SUST_GERAL_S")
    @SequenceGenerator(name = "SPK_T_SUST_GERAL_S",sequenceName = "SPK_T_SUST_GERAL_S",allocationSize = 1)
    private int id;
    
    @Column(name ="NM_DIRETORIO")
    private String diretorio;
    
    @Column(name = "NM_ARQUIVO")
    private String nomeArquivo;
    
    @Column(name = "DT_INPUTDATE")
    private String dtArquivo;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_PROCEDIMENTO")
    private Procedimentos procedimentos;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDiretorio() {
        return diretorio;
    }
    
    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }
    
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
    public String getDtArquivo() {
        return dtArquivo;
    }
    
    public void setDtArquivo(String dtArquivo) {
        this.dtArquivo = dtArquivo;
    }
    
    public Procedimentos getProcedimentos() {
        return procedimentos;
    }
    
    public void setProcedimentos(Procedimentos procedimentos) {
        this.procedimentos = procedimentos;
    }
    
    public String convertDateOracle(String dataEvento){
        try {
            return (new SimpleDateFormat("dd/MMM/yyyy")).format((new SimpleDateFormat("dd/MM/yyyy")).parse(dataEvento));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    
    public String getExtensaoArquivo(String nomeArquivo){
        return FilenameUtils.getExtension(nomeArquivo);
    }
    
    
    public List<Documentos> buildDocumentos (List<MultipartFile> file, Procedimentos procedimentos){
        ArrayList<Documentos> documentos = new ArrayList<>();
        for (MultipartFile d : file) {
            Documentos documento = new Documentos();
            documento.setDiretorio("bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/documentos/");
            documento.setDtArquivo((new SimpleDateFormat("dd/MMM/yyyy").format(new Date())));
            documento.setNomeArquivo(d.getOriginalFilename());
            documento.setProcedimentos(procedimentos);
            try{
                d.transferTo(new File("//bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/documentos/" + d.getOriginalFilename()));
                documentos.add(documento);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return documentos;
    }
    
    
}
