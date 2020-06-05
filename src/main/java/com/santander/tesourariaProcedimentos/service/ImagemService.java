package com.santander.tesourariaProcedimentos.service;

import com.santander.tesourariaProcedimentos.entities.Imagem;
import com.santander.tesourariaProcedimentos.entities.Procedimentos;
import com.santander.tesourariaProcedimentos.repository.ImagensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagemService {
    
    @Autowired
    private ImagensRepository imagemRepository;
    
    @Autowired
    private ProcedimentoService procedimentoService;
    
    public void deleteImagens(Procedimentos procedimentos){
        try{
            if(procedimentos.getImagem() != null)imagemRepository.deleteAll(procedimentos.getImagem());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public List<Procedimentos> findByImagem(MultipartFile imagens){
        try{
            List<Imagem> imagems = new ArrayList<>();
            List<String> nomesImagens = new ArrayList<>();
            File filesDiretorio = new File("//bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/imagens/");
            File file = new File("//bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/imageCompare/" + imagens.getOriginalFilename());
            imagens.transferTo(file);
            
            for (File f: filesDiretorio.listFiles()) {
                if(f.getName().equals("Thumbs.db")) {
                    f.delete();
                    break;
                }
                if(ImageIO.read(file).getHeight() != 800 && ImageIO.read(file).getWidth() != 800){
                    file = new Imagem().reziseImagem(file, "//bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/resize/");
                }
                if(new Imagem().compareImagens(f, file) != null) {
                    nomesImagens.add(new Imagem().compareImagens(f, file).getNomeImagem());
                    imagems.add(new Imagem().compareImagens(f, file));
                }
            }
            file.delete();
            new File("//bsbrsp1153/csa_gbm/_Atividades_SGM_diario/ProcedimenosTesouraria/imageCompare/" + imagens.getOriginalFilename()).delete();
            return procedimentoService.findByImagens(imagemRepository.findByNomeImagemIn(nomesImagens));
            
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
