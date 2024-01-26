package com.vmg.ibo.form.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.model.FormDataEditReq;
import com.vmg.ibo.form.model.FormDataReq;
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
    private ITemplateService templateService;

    @GetMapping("/get-template/{id}")
    public Result<?> getFormsFieldById(@PathVariable Long id) {
        Optional<Template> template = templateService.getTemplateById(id);
        if (!template.isPresent()) {
            return Result.error(404, "Không tìm thấy template");
        }
        return Result.success("Lấy dữ liệu thành công", template.get()) ;
    }

    @GetMapping("/get-template")
    public Result<?> getAll() {
        return Result.success("Lấy dữ liệu thành công", templateService.getAllTemplate());
    }

    @PostMapping("/create-template")
    public Result<?> createForm(@RequestBody FormDataReq formDataReq) {
        return Result.success("Thêm mới thành công", formFieldService.createFormField(formDataReq));
    }

    @PostMapping("/update-template")
    public Result<?> updateForm(@RequestBody FormDataEditReq formDataReq) {
        return Result.success("Cập nhật thành công", formFieldService.editForm(formDataReq));
    }
}