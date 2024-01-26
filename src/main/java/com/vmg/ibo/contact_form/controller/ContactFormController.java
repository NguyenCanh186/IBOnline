package com.vmg.ibo.contact_form.controller;

import com.vmg.ibo.contact_form.model.dto.ContactFormDTO;
import com.vmg.ibo.contact_form.service.IContactFormService;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/contact-form")
public class ContactFormController extends BaseService {
    @Autowired
    private IContactFormService contactFormService;

    @PostMapping("/send")
    public Result<?> sendContactForm(@Validated @RequestBody ContactFormDTO contactFormDTO) {
        return Result.success(contactFormService.create(contactFormDTO));
    }
}
