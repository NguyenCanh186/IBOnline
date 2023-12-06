package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.dto.filter.LogFilter;
import com.vmg.ibo.core.service.log.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/logs")
public class LogController {

    @Autowired
    private ILogService logService;

    @GetMapping
    @PreAuthorize("hasAuthority('log-list')")
    public Result<?> getAllLogAction(LogFilter logFilter) {
        return Result.success(logService.findAllLog(logFilter));
    }
}
