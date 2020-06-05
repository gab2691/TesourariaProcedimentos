package com.santander.tesourariaProcedimentos.repository;

import com.santander.tesourariaProcedimentos.entities.Analista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalistaRepository extends JpaRepository<Analista, Long> {

    public Analista findAnalistaByUser(String matricula);
}
