package com.vmg.ibo.customer.service.code_and_email;

import com.vmg.ibo.customer.model.CodeAndEmail;

import java.util.List;

public interface ICodeAndEmailService {
    void saveCodeAndEmail(CodeAndEmail codeAndEmail);
    void deleteCodeAndEmail(Long id);

    CodeAndEmail findByCode(String code);

    CodeAndEmail findByEmail(String email);

    List<String> findAllCode();

    List<String> findAllEmail();
}
