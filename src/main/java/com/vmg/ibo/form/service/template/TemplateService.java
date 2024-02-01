package com.vmg.ibo.form.service.template;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.entity.TemplateField;
import com.vmg.ibo.form.repository.ITemplateFieldRepository;
import com.vmg.ibo.form.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateService extends BaseService implements ITemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ITemplateFieldRepository iTemplateFieldRepository;

    @Autowired
    private IUserService userService;
    @Override
    public Optional<Template> getTemplateById(Long id) {
        userService.checkChannel(getCurrentUser());
        Optional<Template> template = templateRepository.findById(id);
        if (template.isPresent()) {
            List<TemplateField> templateFields = iTemplateFieldRepository.findByTemplateIdOrderByPriorityDescIdAsc(id);
            template.get().setTemplateFields(templateFields);
        }
        return template;
    }
    @Override
    public Template getTemplateBySlug(String slug) {
        userService.checkChannel(getCurrentUser());
        Template template = templateRepository.findBySlug(slug);
        if (template == null) {
            throw new WebServiceException(HttpStatus.OK.value(),HttpStatus.CONFLICT.value(), "Không tìm thấy nhu cầu");
        }
        List<TemplateField> templateFields = iTemplateFieldRepository.findByTemplateIdOrderByPriorityDescIdAsc(template.getId());
        template.setTemplateFields(templateFields);
        return template;
    }

    @Override
    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public List<Template> getAllTemplate() {
        userService.checkChannel(getCurrentUser());
        return templateRepository.findAll();
    }
}
