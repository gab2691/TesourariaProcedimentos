package com.santander.tesourariaProcedimentos.repository;

import com.santander.tesourariaProcedimentos.entities.Documentos;
import com.santander.tesourariaProcedimentos.entities.Procedimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long> {

    public Documentos findByProcedimentosAndNomeArquivo(Procedimentos procedimento, String nome);
}
