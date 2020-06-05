package com.santander.tesourariaProcedimentos.controllers;


import com.santander.tesourariaProcedimentos.entities.Documentos;
import com.santander.tesourariaProcedimentos.entities.Imagem;
import com.santander.tesourariaProcedimentos.entities.Procedimentos;
import com.santander.tesourariaProcedimentos.repository.DocumentosRepository;
import com.santander.tesourariaProcedimentos.repository.ProcedimentoRepository;
import com.santander.tesourariaProcedimentos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.io.File;
import java.security.Principal;
import java.util.List;

@RestController
public class HomeController {
    
    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    
    @Autowired
    private AnalistaService analistaService;
    
    @Autowired
    private SistemaService sistemaService;
    
    @Autowired ImagemService imagemService;
    
    @Autowired
    private PalavraChaveService palavraChaveService;
    
    @Autowired
    private MalhaBatchService malhaBatchService;
    
    @Autowired
    private ProcedimentoService procedimentoService;
    
    @Autowired
    private DocumentosRepository documentosRepository;
    
    @Autowired
    private  DocumentosService documentosService;

    
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(Principal principal){
        return new ModelAndView("home").addObject("user",principal.getName());
    }
    
    @RequestMapping(value="/searchProcedimentos", method = RequestMethod.POST)
    public ModelAndView procedimentos(@RequestParam(value = "image", required = false) MultipartFile image,
                                      @RequestPart("procedimentos") Procedimentos procedimentos) {
        ModelAndView modelAndView = new ModelAndView("listProcedimentos");
        try{
            if(image != null){
                List<Procedimentos> byImagem = imagemService.findByImagem(image);
                if(byImagem.size() ==0){
                    modelAndView.setStatus(HttpStatus.NO_CONTENT);
                    return modelAndView;
                }
                return modelAndView.addObject("listProced",byImagem);
            };
            List<Procedimentos> byAllParans = procedimentoService.findByAllParans(procedimentos);
            if(byAllParans.size() == 0) {
                modelAndView.setStatus(HttpStatus.NO_CONTENT);
                return modelAndView;
            }
            return modelAndView.addObject("listProced", byAllParans);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    @RequestMapping(value="/updateProcd", method = RequestMethod.POST)
    public Procedimentos updateProcd(@RequestBody Procedimentos procedimentos){
        try{
            return procedimentoRepository.save(procedimentos);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    @RequestMapping(value="/excludProcd", method = RequestMethod.POST)
    public ResponseEntity excludProcd(@RequestBody Procedimentos procedimentos){
        try{
            Procedimentos byId = procedimentoRepository.findById(procedimentos.getId());
            documentosService.deleteDoc(byId);
            imagemService.deleteImagens(byId);
            procedimentoRepository.delete(procedimentos);
            return new ResponseEntity("procedimento deletado", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    @RequestMapping(value="/findById", method = RequestMethod.POST)
    public ModelAndView findById(@RequestBody Procedimentos procedimentos){
        ModelAndView modelAndView = new ModelAndView("procedimentoById");
        try{
            modelAndView.setStatus(HttpStatus.OK);
            return modelAndView.addObject("procedimento",procedimentoRepository.findById(procedimentos.getId()));
        }catch (Exception ex){
            ex.printStackTrace();
            modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return modelAndView;
        }
    }

    @RequestMapping(value="/readDoc", method = RequestMethod.POST)
    public HttpStatus readDoc(@RequestParam Integer procedimentos, @RequestParam String nomeArquivo){
        try{
            File file = new File("//"+documentosRepository.findByProcedimentosAndNomeArquivo(procedimentoRepository.findById(procedimentos),nomeArquivo).getDiretorio() + nomeArquivo);
            Desktop.getDesktop().open(file);
            return HttpStatus.OK;
        }catch (Exception ex){
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    
    @RequestMapping(value="/insertProcedimento", method = RequestMethod.POST)
    public ResponseEntity insertProcedimento(@RequestParam(value = "doc",required = false) List<MultipartFile> doc, @RequestParam(value = "image", required = false) List<MultipartFile> image,
                                             @RequestPart("procedimentos") Procedimentos procedimentos, Principal principal){
        try {
            procedimentos.setDocumentos(new Documentos().buildDocumentos(doc, procedimentos));
            procedimentos.setImagem(new Imagem().buildImagens(image, procedimentos));
            procedimentos.setAnalista(analistaService.getAnalista(principal.getName()));
            procedimentos.setSistemas(sistemaService.findBySigla(procedimentos.getSistemas().getSigla()));
            procedimentos.setPalavraChave(palavraChaveService.findByChave(procedimentos.getPalavraChave().getChave()));
            procedimentos.setMalhaBatch(malhaBatchService.findByJob(procedimentos.getMalhaBatch()));
            procedimentoRepository.save(procedimentos);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
