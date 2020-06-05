package com.santander.tesourariaProcedimentos.entities;

import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Entity
@Table(name = "T_SUST_IMAGEM_S")
public class Imagem {
    
    public Imagem() {
    }
    
    public Imagem(String nomeImagem, Double porcetagem) {
        this.nomeImagem = nomeImagem;
        this.porcetagem = porcetagem;
    }
    
    @Id
    @Column(name = "PK")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPK_T_SUST_GERAL_S")
    @SequenceGenerator(name = "SPK_T_SUST_GERAL_S", sequenceName = "SPK_T_SUST_GERAL_S", allocationSize = 1)
    private int id;
    
    @Column(name = "NM_DIRETORIO")
    private String diretorio;
    
    @Column(name = "NM_IMAGEM")
    private String nomeImagem;
    
    @Column(name = "DT_INPUTDATE")
    private String dtImagem;
    
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "FK_PROCEDIMENTO")
    private Procedimentos procedimentos;
    
    @Transient
    private Double porcetagem;
    
    public Double getPorcetagem() {
        return porcetagem;
    }
    
    public void setPorcetagem(Double porcetagem) {
        this.porcetagem = porcetagem;
    }
    
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
    
    public String getNomeImagem() {
        return nomeImagem;
    }
    
    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }
    
    public String getDtImagem() {
        return dtImagem;
    }
    
    public void setDtImagem(String dtImagem) {
        this.dtImagem = dtImagem;
    }
    
    public Procedimentos getProcedimentos() {
        return procedimentos;
    }
    
    public void setProcedimentos(Procedimentos procedimentos) {
        this.procedimentos = procedimentos;
    }
    
    public List<Imagem> buildImagens(List<MultipartFile> image, Procedimentos procedimentos){
        ArrayList<Imagem> imagens  = new ArrayList<>();
        for (MultipartFile d : image) {
            Imagem imagem = new Imagem();
            imagem.setDiretorio("bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/imagens/");
            imagem.setDtImagem((new SimpleDateFormat("dd/MMM/yyyy").format(new Date())));
            imagem.setNomeImagem(d.getOriginalFilename());
            imagem.setProcedimentos(procedimentos);
            
            try{
                File file = new File("//bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/resize/" + d.getOriginalFilename());
                d.transferTo(file);
                new File(String.valueOf(this.reziseImagem(file, "//bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/imagens/")));
                file.delete();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            imagens.add(imagem);
        }
        return imagens;
    }
    
    public Imagem compareImagens(File fileA, File fileB) throws IOException {
        System.out.println("arquivo da pasta " + fileA.getName() + " / arquivo de comparação " + fileB.getName());
        BufferedImage imgA = ImageIO.read(fileA);
        BufferedImage imgB = ImageIO.read(fileB);
        
        int width1 = imgA.getWidth();
        int width2 = imgB.getWidth();
        int height1 = imgA.getHeight();
        int height2 = imgB.getHeight();
    
        if ((width1 != width2) || (height1 != height2)) System.out.println("Error: Images dimensions mismatch");
        {
            long difference = 0;
            for (int y = 0; y < height1; y++)
            {
                for (int x = 0; x < width1; x++)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;
                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }
        
            double total_pixels = width1 * height1 * 3;
            double avg_different_pixels = difference / total_pixels;
            Double percentage = (avg_different_pixels / 255) * 100;
        
            System.out.println("Difference Percentage--> " + percentage + "%");
            AbstractMap.SimpleEntry<File, Double> map = new AbstractMap.SimpleEntry<>(fileA, 100 - percentage );
            if(percentage < 16) return new Imagem(fileA.getName(),percentage);
        }
        
        return null;
    }
    
    public File reziseImagem(File file, String path) throws IOException {
        Image img = ImageIO.read(file);
        BufferedImage tempPng = resizeImage(img, 800, 800);
        File newFilePNG = new File(path + file.getName());
        ImageIO.write(tempPng, "png", newFilePNG);
        return newFilePNG;
    }
    
    public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
}
