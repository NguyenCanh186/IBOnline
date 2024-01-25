package com.vmg.ibo.form.controller;

import com.vmg.ibo.core.base.BaseFilter;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.model.DemandReq;
import com.vmg.ibo.form.model.FormUpdateStatusReq;
import com.vmg.ibo.form.service.form.IFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/form")
public class FormController {
    @Autowired
    private IFormService formService;

    @GetMapping("/{id}")
    public Result<?> getFormsFieldById(@PathVariable Long id) {
        FormDTO form = formService.getFormById(id);
        return Result.success("Lấy dữ liệu thành công", form) ;
    }

    @GetMapping("/cms/{id}")
    @PreAuthorize("hasAuthority('form-detail')")
    public Result<?> getFormsCMSFieldById(@PathVariable Long id) {
        FormDTO form = formService.getFormCMSById(id);
        return Result.success("Lấy dữ liệu thành công", form) ;
    }

    @PutMapping("/{id}/connect")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody FormUpdateStatusReq formUpdateStatusReq) {
        Form form = formService.connect(id, formUpdateStatusReq);
        return Result.success("Cập nhật trạng thái thành công", form);
    }

    @GetMapping("/get-all-demand")
    public Result<?> getAllDemand(DemandReq demandReq, Pageable pageable) {
        return Result.success("Lấy dữ liệu thành công", formService.getAllDemand(demandReq, pageable));
    }

    @GetMapping("/user/{id}/get-all-forms")
    public Result<?> getAllDemandByUser(@PathVariable Long id, BaseFilter filter) {
        return Result.success("Lấy dữ liệu thành công", formService.getAllFormByUser(id, filter));
    }
}
