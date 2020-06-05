package com.santander.tesourariaProcedimentos.repository;

import com.santander.tesourariaProcedimentos.entities.Imagem;
import com.santander.tesourariaProcedimentos.entities.Procedimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface ImagensRepository extends JpaRepository<Imagem, Long> {
    
    public Imagem findFirstByNomeImagem(String nome);
    
    public List<Imagem> findByNomeImagemIn(List<String> nomes);
    
}
