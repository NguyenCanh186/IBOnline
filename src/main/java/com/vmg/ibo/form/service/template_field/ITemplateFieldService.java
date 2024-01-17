package com.vmg.ibo.form.service.template_field;

import com.vmg.ibo.form.entity.TemplateField;

import java.util.Optional;

public interface ITemplateFieldService {
    TemplateField createTemplateField(TemplateField templateField);

    Optional<TemplateField> getTemplateFieldById(Long id);
}
