package com.vmg.ibo.form.service.form_field;

import com.vmg.ibo.form.entity.FormField;

import java.util.List;

public interface IFormFieldService {
    List<FormField> getFormFieldsByFormId(Long formId);
    List<FormField> getFormFieldsByFormSlug(String slug);
}
