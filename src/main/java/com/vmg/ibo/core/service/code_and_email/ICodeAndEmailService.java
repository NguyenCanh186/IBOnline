package com.vmg.ibo.core.service.code_and_email;

import com.vmg.ibo.core.model.entity.CodeAndEmail;

import java.util.List;

public interface ICodeAndEmailService {
    void saveCodeAndEmail(CodeAndEmail codeAndEmail);
    void deleteCodeAndEmail(Long id);

    CodeAndEmail findByCode(String code);

    List<String> findAllCode();

    List<String> findAllEmail();
}
