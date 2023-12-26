package com.vmg.ibo.demand.service;

import com.vmg.ibo.demand.entity.Demand;

import java.util.List;

public interface IDemandService {
    List<Demand> getAllDemand();

    Demand getDemandById(Long id);
}
