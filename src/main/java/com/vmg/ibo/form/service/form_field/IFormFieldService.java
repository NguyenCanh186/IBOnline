package com.vmg.ibo.form.service.form_field;

import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.FormField;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.model.FormDataEditReq;
import com.vmg.ibo.form.model.FormDataReq;

import java.util.List;

public interface IFormFieldService {
    List<FormField> getFormFieldsByFormId(Long formId);

    Form createFormField(FormDataReq formDataReq);

    Form getFormByUserAndTemplate(Long idUser, Long idTemplate);

    Form editForm(FormDataEditReq formDataReq);
}
