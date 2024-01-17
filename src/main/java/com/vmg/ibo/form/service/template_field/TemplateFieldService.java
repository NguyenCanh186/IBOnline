package com.vmg.ibo.form.service.template_field;

import com.vmg.ibo.form.entity.TemplateField;
import com.vmg.ibo.form.repository.ITemplateFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateFieldService implements ITemplateFieldService{
    @Autowired
    private ITemplateFieldRepository templateFieldRepository;
    @Override
    public TemplateField createTemplateField(TemplateField templateField) {
        return templateFieldRepository.save(templateField);
    }

    @Override
    public Optional<TemplateField> getTemplateFieldById(Long id) {
        return templateFieldRepository.findById(id);
    }
}
