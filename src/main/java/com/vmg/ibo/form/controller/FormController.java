package com.vmg.ibo.form.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.service.form.IFormService;
import com.vmg.ibo.form.service.form_data.IFormDataService;
import com.vmg.ibo.form.service.form_field.IFormFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/form")
public class FormController {
    @Autowired
    private IFormService formService;
    @Autowired
    private IFormFieldService formFieldService;
    @Autowired
    private IFormDataService formDataService;

    @GetMapping("/get-forms/{id}")
    @PreAuthorize("hasAuthority('get-form')")
    public Result<?> getFormsFieldById(@PathVariable Long id) {
        Optional<Form> form = formService.getFormById(id);
        if (!form.isPresent()) {
            return Result.error(404, "Không tìm thấy form");
        }
        return Result.success("Lấy dữ liệu thành công", form.get()) ;
    }

}
