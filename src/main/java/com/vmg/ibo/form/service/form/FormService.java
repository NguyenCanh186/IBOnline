package com.vmg.ibo.form.service.form;

import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.repository.IFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService implements IFormService{
    @Autowired
    private IFormRepository formRepository;
    @Override
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    @Override
    public Optional<Form> getFormById(Long id) {
        return formRepository.findById(id);
    }


}
