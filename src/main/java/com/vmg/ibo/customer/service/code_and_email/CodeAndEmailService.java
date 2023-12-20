package com.vmg.ibo.customer.service.code_and_email;

import com.vmg.ibo.customer.model.CodeAndEmail;
import com.vmg.ibo.customer.repository.ICodeAndEmailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public CodeAndEmail findByCode(String code) {
        return codeAndEmailRepo.findByCode(code);
    }

    @Override
    public List<String> findAllCode() {
        return codeAndEmailRepo.findAllCode();
    }

    @Override
    public List<String> findAllEmail() {
        return codeAndEmailRepo.findAllEmail();
    }
}
