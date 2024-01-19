package com.vmg.ibo.form.service.form_field;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.FormField;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.entity.TemplateField;
import com.vmg.ibo.form.model.FormDataReq;
import com.vmg.ibo.form.model.TemplateFieldReq;
import com.vmg.ibo.form.repository.IFormFieldRepository;
import com.vmg.ibo.form.service.form.IFormService;
import com.vmg.ibo.form.service.template.ITemplateService;
import com.vmg.ibo.form.service.template_field.ITemplateFieldService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FormFieldService extends BaseService implements IFormFieldService {
    @Autowired
    private IFormFieldRepository formFieldRepository;

    @Autowired
    private IFormService formService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private ITemplateFieldService templateFieldService;

    @Override
    public List<FormField> getFormFieldsByFormId(Long formId) {
        return formFieldRepository.getFormFieldsByFormId(formId);
    }

    @Override
    public Form createFormField(FormDataReq formDataReq) {
        Optional<Template> template = templateService.getTemplateById(formDataReq.getIdTemplate());
        if (!template.isPresent()) {
            throw new WebServiceException(HttpStatus.OK.value(), "Không tìm thấy Form");
        }
        if (!checkListField(formDataReq.getTemplateFieldReqs())) {
            throw new WebServiceException(HttpStatus.OK.value(), "Dữ liệu không hợp lệ");
        }
        Form form = new Form();
        form.setTemplate(template.get());
        form.setUser(getCurrentUser());
        form.setCreatedAt(new Date());
        form.setStatus(0);
        Form formCreated = formService.createForm(form);
        List<FormField> formFields = new ArrayList<>();
        for (int i = 0; i < formDataReq.getTemplateFieldReqs().size(); i++) {
            FormField formField = new FormField();
            Optional<TemplateField> templateField = templateFieldService.getTemplateFieldById(formDataReq.getTemplateFieldReqs().get(i).getIdTemplateField());
            if (!templateField.isPresent()) {
                throw new WebServiceException(HttpStatus.OK.value(), "Template Field không tồn tại");
            }
            formField.setForm(formCreated);
            formField.setCreatedAt(new Date());
            formField.setTemplateField(templateField.get());
            formField.setValue(formDataReq.getTemplateFieldReqs().get(i).getValue());
            FormField formField1 = formFieldRepository.save(formField);
            formFields.add(formField1);
        }
        formCreated.setFormFields(formFields);

        return formCreated;
    }

    private boolean checkListField(List<TemplateFieldReq> request) {
        for (TemplateFieldReq templateFieldReq : request) {
            if (!isValidField(templateFieldReq.getIdTemplateField(), templateFieldReq)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidField(Long templateFieldId, TemplateFieldReq request) {
        Optional<TemplateField> templateField = templateFieldService.getTemplateFieldById(templateFieldId);
        if (!templateField.isPresent()) {
            throw new WebServiceException(HttpStatus.OK.value(), "Template Field không tồn tại");
        }
        String rules = templateField.get().getRules();
        String type = templateField.get().getType();
        if (type.equals("number")) {
            try {
                long valueRequest = Long.parseLong(request.getValue());
            } catch (NumberFormatException e) {
                throw new WebServiceException(HttpStatus.OK.value(), "Dữ liệu không hợp lệ");
            }
        }
        if(rules != null && !rules.isEmpty()) {
            JSONObject jsonObject = (JSONObject) JSONValue.parse(rules);
            if(jsonObject.containsKey("required")) {
                boolean isRequired = (boolean) jsonObject.get("required");
                if (isRequired) {
                    if (request.getValue() != null && !request.getValue().isEmpty()) {
                        if (jsonObject.containsKey("max")) {
                            Integer max = (Integer) jsonObject.get("max");
                            if (request.getValue().length() <= max) {
                                return true;
                            } else {
                                throw new WebServiceException(HttpStatus.OK.value(), templateField.get().getName() + " không được vượt quá " + max + " ký tự");
                            }
                        }
                        if (jsonObject.containsKey("greaterThan")) {
                            JSONObject greaterThan = (JSONObject) jsonObject.get("greaterThan");
                            long target = (long) greaterThan.get("target");
                            if (type.equals("number")) {
                                long value = Long.parseLong(request.getValue());
                                if (value > target) {
                                    return true;
                                } else {
                                    throw new WebServiceException(HttpStatus.OK.value(), templateField.get().getName() + " phải lớn hơn " + target);
                                }
                            }
                        }
                        if (jsonObject.containsKey("lessThanOrEqual")) {
                            JSONObject lessThan = (JSONObject) jsonObject.get("lessThanOrEqual");
                            long target = (long) lessThan.get("target");
                            if (type.equals("number")) {
                                long value = Long.parseLong(request.getValue());
                                if (value < target) {
                                    return true;
                                } else {
                                    throw new WebServiceException(HttpStatus.OK.value(), templateField.get().getName() + " phải nhỏ hơn " + target);
                                }
                            }
                        }
                    } else {
                        throw new WebServiceException(HttpStatus.OK.value(), templateField.get().getName() + " không được để trống");
                    }
                }
            }
        }
        return true;
    }
}
