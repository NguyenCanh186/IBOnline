package com.vmg.ibo.form.service.form;

import com.vmg.ibo.form.dto.DemandDTO;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.model.DemandReq;
import com.vmg.ibo.form.model.FormUpdateStatusReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFormService {
    List<Form> getAllForms();
    FormDTO getFormById(Long id);
    Form createForm(Form form);

    List<String> getAllCodeDemand();

    Form connect(Long id, FormUpdateStatusReq formUpdateStatusReq);

    Page<DemandDTO> getAllDemand(DemandReq demandReq, Pageable pageable);

}
