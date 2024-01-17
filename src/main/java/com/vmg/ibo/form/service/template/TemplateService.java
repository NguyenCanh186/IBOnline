package com.vmg.ibo.form.service.template;

import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateService implements ITemplateService {

    @Autowired
    private TemplateRepository templateRepository;
    @Override
    public Optional<Template> getTemplateById(Long id) {
        return templateRepository.findById(id);
    }

    @Override
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }
}
