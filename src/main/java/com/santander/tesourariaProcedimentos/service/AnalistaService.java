package com.santander.tesourariaProcedimentos.service;

import com.santander.tesourariaProcedimentos.entities.Analista;
import com.santander.tesourariaProcedimentos.repository.AnalistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalistaService {
    
    @Autowired
    private AnalistaRepository analistaRepository;
    
    public Analista getAnalista(String matricula){
        try{
            Analista analistaByUser = analistaRepository.findAnalistaByUser(matricula);
            analistaByUser.getUser();
            return analistaByUser;
        }catch (Exception ex) {
            return analistaRepository.save(new Analista().setUser(matricula));
        }
    }
}
