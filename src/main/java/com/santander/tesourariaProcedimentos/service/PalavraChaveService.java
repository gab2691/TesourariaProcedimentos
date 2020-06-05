package com.santander.tesourariaProcedimentos.service;

import com.santander.tesourariaProcedimentos.entities.PalavraChave;
import com.santander.tesourariaProcedimentos.repository.PalavraChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PalavraChaveService {
    
    @Autowired
    private PalavraChaveRepository palavraChaveRepository;
    
    public PalavraChave findByChave(String chave){
        try{
            chave = chave.trim();
            if(chave.equals("")) return null;
            PalavraChave byChave = palavraChaveRepository.findByChave(chave);
            byChave.getId();
            return byChave;
        }catch (Exception ex){
            return palavraChaveRepository.save(new PalavraChave(chave));
        }
    }
}
