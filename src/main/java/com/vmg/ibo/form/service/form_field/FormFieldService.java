package com.vmg.ibo.form.service.form_field;

import com.vmg.ibo.form.entity.FormField;
import com.vmg.ibo.form.repository.IFormFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormFieldService implements IFormFieldService{
    @Autowired
    private IFormFieldRepository formFieldRepository;

    @Override
    public List<FormField> getFormFieldsByFormId(Long formId) {
        return formFieldRepository.getFormFieldsByFormId(formId);
    }

    @Override
    public List<FormField> getFormFieldsByFormSlug(String slug) {
        return formFieldRepository.getFormFieldsByFormSlug(slug);
    }
}
