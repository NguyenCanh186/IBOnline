package com.vmg.ibo.contact_form.service;

import com.vmg.ibo.contact_form.model.dto.ContactFormDTO;

public interface IContactFormService {
    ContactFormDTO create(ContactFormDTO contactFormDTO);
}
