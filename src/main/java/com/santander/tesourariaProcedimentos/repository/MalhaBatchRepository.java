package com.santander.tesourariaProcedimentos.repository;

import com.santander.tesourariaProcedimentos.entities.MalhaBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MalhaBatchRepository extends JpaRepository<MalhaBatch, Long> {

    public MalhaBatch findByNomeJob(String job);
}
