package com.santander.tesourariaProcedimentos.service;

import com.santander.tesourariaProcedimentos.entities.Sistemas;
import com.santander.tesourariaProcedimentos.repository.SistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SistemaService {

    @Autowired
    private SistemaRepository sistemaRepository;
    
    public Sistemas findBySigla(String sigla){
        try{
            if(sigla.equals("")) return null;
            Sistemas bySigla = sistemaRepository.findBySigla(sigla);
            bySigla.getId();
            return bySigla;
        }catch (Exception ex){
            return sistemaRepository.save(new Sistemas(sigla));
        }
    }
}
