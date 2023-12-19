package com.vmg.ibo.core.service.code_and_email;

import com.vmg.ibo.core.model.entity.CodeAndEmail;
import com.vmg.ibo.core.repository.ICodeAndEmailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeAndEmailService implements ICodeAndEmailService {
    @Autowired
    private ICodeAndEmailRepo codeAndEmailRepo;

    @Override
    public void saveCodeAndEmail(CodeAndEmail codeAndEmail) {
        codeAndEmailRepo.save(codeAndEmail);
    }

    @Override
    public void deleteCodeAndEmail(Long id) {
        codeAndEmailRepo.deleteById(id);
    }
}
