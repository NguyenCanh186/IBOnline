package com.vmg.ibo.form.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form.dto.FormDTO;
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
@RequestMapping("/v1/form")
public class FormController {
    @Autowired
    private IFormFieldService formFieldService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IFormService iFormService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('get-form')")
    public Result<?> getFormsFieldById(@PathVariable Long id) {
        FormDTO form = iFormService.getFormById(id);
        return Result.success("Lấy dữ liệu thành công", form) ;
    }
}
