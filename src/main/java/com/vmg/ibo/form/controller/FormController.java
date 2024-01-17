package com.vmg.ibo.form.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.model.FormDataReq;
import com.vmg.ibo.form.service.form.IFormService;
import com.vmg.ibo.form.service.template.ITemplateService;
import com.vmg.ibo.form.service.template_field.ITemplateFieldService;
import com.vmg.ibo.form.service.form_field.IFormFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/template")
public class FormController {
    @Autowired
    private IFormService formService;
    @Autowired
    private IFormFieldService formFieldService;
    @Autowired
    private ITemplateFieldService formDataService;

    @Autowired
    private ITemplateService templateService;

    @GetMapping("/get-template/{id}")
    @PreAuthorize("hasAuthority('get-form')")
    public Result<?> getFormsFieldById(@PathVariable Long id) {
        Optional<Template> template = templateService.getTemplateById(id);
        if (!template.isPresent()) {
            return Result.error(404, "Không tìm thấy template");
        }
        return Result.success("Lấy dữ liệu thành công", template.get()) ;
    }

    @PostMapping("/create-form")
    @PreAuthorize("hasAuthority('create-form')")
    public Result<?> createForm(@RequestBody FormDataReq formDataReq) {
        return Result.success("Thêm mói thành công", formFieldService.createFormField(formDataReq));
    }

}