package com.santander.tesourariaProcedimentos.repository;

import com.santander.tesourariaProcedimentos.entities.PalavraChave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalavraChaveRepository extends JpaRepository<PalavraChave, Long> {
    
    public PalavraChave findByChave (String chave);
}
