package com.santander.tesourariaProcedimentos.repository;

import com.santander.tesourariaProcedimentos.entities.Imagem;
import com.santander.tesourariaProcedimentos.entities.Procedimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimentos, Long> {

    public Procedimentos findById(Integer id);
    
    public List<Procedimentos> findByImagemIn(List<Imagem> imagens);
}
