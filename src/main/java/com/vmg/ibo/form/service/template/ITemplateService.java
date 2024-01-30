package com.vmg.ibo.form.service.template;

import com.vmg.ibo.form.entity.Template;

import java.util.List;
import java.util.Optional;

public interface ITemplateService {
    Optional<Template> getTemplateById(Long id);

    Template createTemplate(Template template);

    List<Template> getAllTemplate();

    Template getTemplateBySlug(String slug);
}
