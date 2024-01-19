package com.vmg.ibo.form.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.model.FormDataReq;
import com.vmg.ibo.form.service.form.IFormService;
import com.vmg.ibo.form.service.template.ITemplateService;
import com.vmg.ibo.form.service.form_field.IFormFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/template")
public class TemplateController {
    @Autowired
    private IFormFieldService formFieldService;

    @Autowired
    private IFormService iFormService;

    @Autowired
    private ITemplateService templateService;

    @GetMapping("/get-template/{id}")
    @PreAuthorize("hasAuthority('get-form')")
    public Result<?> getFormsFieldById(@PathVariable Long id) {
        FormDTO form = iFormService.getFormById(id);
        return Result.success("Lấy dữ liệu thành công", form);
    }

    @GetMapping("/get-template")
    @PreAuthorize("hasAuthority('get-form')")
    public Result<?> getAll() {
        return Result.success("Lấy dữ liệu thành công", templateService.getAllTemplate());
    }

    @PostMapping("/create-template")
    @PreAuthorize("hasAuthority('create-form')")
    public Result<?> createForm(@RequestBody FormDataReq formDataReq) {
        return Result.success("Thêm mói thành công", formFieldService.createFormField(formDataReq));
    }

}
