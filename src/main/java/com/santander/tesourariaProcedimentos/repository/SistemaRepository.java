package com.santander.tesourariaProcedimentos.repository;

import com.santander.tesourariaProcedimentos.entities.Sistemas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaRepository extends JpaRepository<Sistemas, Long> {

    public Sistemas findBySigla(String sigla);
}
