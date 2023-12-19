package com.vmg.ibo.core.service.code_and_email;

import com.vmg.ibo.core.model.entity.CodeAndEmail;

public interface ICodeAndEmailService {
    void saveCodeAndEmail(CodeAndEmail codeAndEmail);
    void deleteCodeAndEmail(Long id);
}
