package com.vmg.ibo.demand.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.demand.service.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/demand")
public class DemandController {
    @Autowired
    private IDemandService demandService;

    @GetMapping
    public Result<?> getAllDemand(){
        return Result.success(demandService.getAllDemand());
    }
}
