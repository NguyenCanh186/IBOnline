package com.vmg.ibo.form.service.form;

import com.vmg.ibo.form.entity.Form;

import java.util.List;
import java.util.Optional;

public interface IFormService {
    List<Form> getAllForms();
    Optional<Form> getFormById(Long id);
    Form createForm(Form form);
}