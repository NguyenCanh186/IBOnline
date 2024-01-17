package com.vmg.ibo.form.service.form_field;

import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.FormField;
import com.vmg.ibo.form.model.FormDataReq;

import java.util.List;

public interface IFormFieldService {
    List<FormField> getFormFieldsByFormId(Long formId);

    Form createFormField(FormDataReq formDataReq);
}
