package com.vmg.ibo.demand.service;

import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.repository.IDemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DemandService implements IDemandService{
    @Autowired
    private IDemandRepository demandRepository;
    @Override
    public List<Demand> getAllDemand() {
        return demandRepository.findAll();
    }

    @Override
    public Demand getDemandById(Long id) {
        return demandRepository.findById(id).orElse(null);
    }
}
