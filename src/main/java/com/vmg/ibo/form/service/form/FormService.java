package com.vmg.ibo.form.service.form;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.dto.FormSuggestDTO;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.repository.IFormRepository;
import com.vmg.ibo.form.repository.TemplateRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormService extends BaseService implements IFormService{
    private static final Logger logger = LoggerFactory.getLogger(IFormService.class);

    @Autowired
    private IFormRepository formRepository;

    @Autowired
    private TemplateRepository templateRepository;
    @Override
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    @Override
    public FormDTO getFormById(Long id) {
        Optional<Form> form = formRepository.findById(id);
        List<Long> list = new ArrayList<>();
        if (form.isPresent()) {
            List<Template> templateList;
            if(form.get().getTemplate().getType() == 2) {
                templateList = templateRepository.findAllByType(1);
            } else {
                templateList = templateRepository.findAllByType(2);
            }
            for(Template template : templateList){
                if(form.get().getTemplate().getTag().contains(template.getTag())){
                    list.add(template.getId());
                }
            }
            List<Form> listSuggestLatest = formRepository.findTop3ByTemplateIdInAndUserIdNotOrderByCreatedAtDesc(list, getCurrentUser().getId());
            ModelMapper modelMapper = new ModelMapper();
            FormDTO formDTO = modelMapper.map(form.get(), FormDTO.class);
            formDTO.setSuggestLatest(listSuggestLatest.stream().map(x -> modelMapper.map(x, FormSuggestDTO.class)).collect(Collectors.toList()));
            return formDTO;
        } else {
            throw new WebServiceException(HttpStatus.OK.value(), "Không tìm thấy nhu cầu hợp lệ");
        }
    }

    @Override
    public Form createForm(Form form) {
        return formRepository.save(form);
    }


}
