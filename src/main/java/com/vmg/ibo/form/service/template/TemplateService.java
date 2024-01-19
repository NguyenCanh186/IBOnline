package com.vmg.ibo.form.service.template;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateService extends BaseService implements ITemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private IUserService userService;
    @Override
    public Optional<Template> getTemplateById(Long id) {
        userService.checkChannel(getCurrentUser());
        return templateRepository.findById(id);
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
