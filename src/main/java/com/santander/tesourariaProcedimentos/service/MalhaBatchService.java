package com.santander.tesourariaProcedimentos.service;

import com.santander.tesourariaProcedimentos.entities.MalhaBatch;
import com.santander.tesourariaProcedimentos.repository.MalhaBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MalhaBatchService {
    
    @Autowired
    private MalhaBatchRepository malhaBatchRepository;
    
    public MalhaBatch findByJob(MalhaBatch job){
        try{
            if(job == null) return null;
            MalhaBatch byNomeJob = malhaBatchRepository.findByNomeJob(job.getNomeJob());
            byNomeJob.getId();
            return byNomeJob;
        }catch (Exception ex){
            return malhaBatchRepository.save(new MalhaBatch(job.getNomeJob()));
        }
    }
}
