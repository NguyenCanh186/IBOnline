package com.vmg.ibo.deal.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.deal.model.dto.DealDTO;
import com.vmg.ibo.deal.model.dto.DealFilter;
import com.vmg.ibo.deal.service.IDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/deals")
public class DealController {
    @Autowired
    private IDealService dealService;

    @GetMapping
    @PreAuthorize("hasAuthority('deal-list')")
    public Result<?> getAllDeals(DealFilter filter) {
        return Result.success("Lấy dữ liệu thành công", dealService.getAllDeals(filter));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('deal-update-status')")
    public Result<?> updateStatusDeal(@PathVariable Long id, @RequestBody DealDTO dealDTO) {
        return Result.success("Cập nhật trạng thái thành công", dealService.updateStatus(id, dealDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('deal-get')")
    public Result<?> getDealById(@PathVariable Long id) {
        return Result.success("Lấy dữ liệu thành công", dealService.findById(id));
    }
}
