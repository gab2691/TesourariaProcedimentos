package com.santander.tesourariaProcedimentos.service;

import com.santander.tesourariaProcedimentos.entities.Procedimentos;
import com.santander.tesourariaProcedimentos.repository.DocumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentosService {
    
    @Autowired
    private DocumentosRepository documentosRepository;
    
    public void deleteDoc(Procedimentos procedimentos){
        try{
            if (procedimentos.getDocumentos() != null) documentosRepository.deleteAll(procedimentos.getDocumentos());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
