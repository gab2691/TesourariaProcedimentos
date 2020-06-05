package com.santander.tesourariaProcedimentos.service;


import com.santander.tesourariaProcedimentos.entities.Imagem;
import com.santander.tesourariaProcedimentos.entities.Procedimentos;
import com.santander.tesourariaProcedimentos.repository.ProcedimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ProcedimentoService {
    
    @Autowired
    private ProcedimentoRepository procedimentoRepository;
    
    
    public List<Procedimentos> findByImagens(List<Imagem> imagens){
        try{
            return procedimentoRepository.findByImagemIn(imagens);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    
    public List<Procedimentos> findByAllParans (Procedimentos procedimentos) throws ParseException {
        Example<Procedimentos> searchExample =
             Example.of(procedimentos, ExampleMatcher.matching()
                     .withMatcher("descProcedimento", GenericPropertyMatchers.ignoreCase().contains())
                     .withMatcher("sistemas.sigla", GenericPropertyMatchers.ignoreCase())
                     .withMatcher("malhaBatch.nomeJob", GenericPropertyMatchers.ignoreCase().contains())
                     .withMatcher("palavraChave.chave", GenericPropertyMatchers.contains().ignoreCase())
                     .withIgnorePaths("sistemas.id").withIgnorePaths("malhaBatch.id").withIgnorePaths("palavraChave.id")
                     
                     .withIgnoreNullValues());
    
            return (List)this.procedimentoRepository.findAll(searchExample);
    }
}
