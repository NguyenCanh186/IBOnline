package com.vmg.ibo.form.service.form_data;

import com.vmg.ibo.form.repository.IFormDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDataService implements IFormDataService{
    @Autowired
    private IFormDataRepository formDataRepository;
}
