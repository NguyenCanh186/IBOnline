package com.vmg.ibo.form.service.form_field;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.customer.model.DataModel;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.FormField;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.entity.TemplateField;
import com.vmg.ibo.form.model.FormDataEditReq;
import com.vmg.ibo.form.model.FormDataReq;
import com.vmg.ibo.form.model.TemplateFieldEditReq;
import com.vmg.ibo.form.model.TemplateFieldReq;
import com.vmg.ibo.form.repository.IFormFieldRepository;
import com.vmg.ibo.form.repository.IFormRepository;
import com.vmg.ibo.form.repository.ITemplateFieldRepository;
import com.vmg.ibo.form.service.form.IFormService;
import com.vmg.ibo.form.service.template.ITemplateService;
import com.vmg.ibo.form.service.template_field.ITemplateFieldService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormFieldService extends BaseService implements IFormFieldService {
    @Autowired
    private IFormFieldRepository formFieldRepository;

    @Autowired
    private ITemplateFieldRepository templateFieldRepository;

    @Autowired
    private IFormService formService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private ITemplateFieldService templateFieldService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFormRepository formRepository;

    @Override
    public List<FormField> getFormFieldsByFormId(Long formId) {
        return formFieldRepository.getFormFieldsByFormId(formId);
    }

    @Override
    public Form editForm(FormDataEditReq formDataReq) {
        Optional<Form> form = formRepository.findById(formDataReq.getIdForm());
        if (!form.isPresent()) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy Form");
        }
        if (form.get().getStatus() != 0) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Form đã được kết nối");
        }
        List<TemplateField> templateFields = templateFieldRepository.getTemplateFieldsByTemplateId(form.get().getTemplate().getId());
        String templateFieldIds = templateFields.stream()
                .map(templateField -> String.valueOf(templateField.getId()))
                .collect(Collectors.joining(","));
        String[] templateFieldIdArray = templateFieldIds.split(",");
        for (int i = 0; i < formDataReq.getTemplateFieldReqs().size(); i++) {
            long templateFieldId = formDataReq.getTemplateFieldReqs().get(i).getIdTemplateField();
            boolean idExists = false;
            for (String id : templateFieldIdArray) {
                if (Long.parseLong(id) == templateFieldId) {
                    idExists = true;
                    break;
                }
            }
            if (!idExists) {
                throw new WebServiceException(200, 409, "Form sai định dạng");
            }
        }
        List<FormField> formFields = formFieldRepository.findAllByFormId(formDataReq.getIdForm());
        if (!checkListFieldEdit(formDataReq.getTemplateFieldReqs())) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Dữ liệu không hợp lệ");
        }
        for (FormField formField : formFields) {
            for (int j = 0; j < formDataReq.getTemplateFieldReqs().size(); j++) {
                if (Objects.equals(formField.getTemplateField().getId(), formDataReq.getTemplateFieldReqs().get(j).getIdTemplateField())) {
                    Optional<TemplateField> templateField = templateFieldService.getTemplateFieldById(formDataReq.getTemplateFieldReqs().get(j).getIdTemplateField());
                    if (!templateField.isPresent()) {
                        throw new WebServiceException(HttpStatus.OK.value(), 409, "Template Field không tồn tại");
                    }
                    formField.setValue(formDataReq.getTemplateFieldReqs().get(j).getValue().trim());
                    formField.setUpdatedAt(new Date());
                    formFieldRepository.save(formField);
                }
            }
        }
        return form.get();
    }

    @Override
    public Form createFormField(FormDataReq formDataReq) {
        if (!getCurrentUser().isInfo()) {
            throw new WebServiceException(HttpStatus.OK.value(), "Vui lòng cập nhật thông tin cá nhân trước khi đăng nhu cầu");
        }
        List<TemplateField> templateFields = templateFieldRepository.getTemplateFieldsByTemplateId(formDataReq.getIdTemplate());
        String templateFieldIds = templateFields.stream()
                .map(templateField -> String.valueOf(templateField.getId()))
                .collect(Collectors.joining(","));

        for (int i = 0; i < formDataReq.getTemplateFieldReqs().size(); i++) {
            long templateFieldId = formDataReq.getTemplateFieldReqs().get(i).getIdTemplateField();
            String[] templateFieldIdArray = templateFieldIds.split(",");
            boolean idExists = false;
            for (String id : templateFieldIdArray) {
                if (Long.parseLong(id) == templateFieldId) {
                    idExists = true;
                    break;
                }
            }
            if (!idExists) {
                throw new WebServiceException(200, 409, "Form sai định dạng");
            }
        }
        userService.checkChannel(getCurrentUser());
        Optional<Template> template = templateService.getTemplateById(formDataReq.getIdTemplate());
        if (!template.isPresent()) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy Form");
        }
        if (!checkListField(formDataReq.getTemplateFieldReqs())) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Dữ liệu không hợp lệ");
        }
        List<String> listFormCode = formService.getAllCodeDemand();
        int maxNumber = listFormCode.stream()
                .map(s -> Integer.parseInt(s.substring(3)))
                .max(Comparator.naturalOrder()).orElse(0) + 1;
        String paddedMaxNumber = String.format("%06d", maxNumber);
        String formCode = DataModel.DEMAND_FORM_CODE + paddedMaxNumber;
        Form form = new Form();
        form.setTemplate(template.get());
        form.setUser(getCurrentUser());
        form.setCreatedAt(new Date());
        form.setStatus(0);
        form.setCodeDemand(formCode);
        Form formCreated = formService.createForm(form);
        List<FormField> formFields = new ArrayList<>();
        for (int i = 0; i < formDataReq.getTemplateFieldReqs().size(); i++) {
            FormField formField = new FormField();
            Optional<TemplateField> templateField = templateFieldService.getTemplateFieldById(formDataReq.getTemplateFieldReqs().get(i).getIdTemplateField());
            if (!templateField.isPresent()) {
                throw new WebServiceException(HttpStatus.OK.value(), 409, "Template Field không tồn tại");
            }
            formField.setForm(formCreated);
            formField.setCreatedAt(new Date());
            formField.setTemplateField(templateField.get());
            formField.setValue(formDataReq.getTemplateFieldReqs().get(i).getValue().trim());
            FormField formField1 = formFieldRepository.save(formField);
            formFields.add(formField1);
        }
        formCreated.setFormFields(formFields);

        return formCreated;
    }

    @Override
    public Form getFormByUserAndTemplate(Long idUser, Long idTemplate) {
        return formRepository.findByTemplateIdAndUserId(idUser, idTemplate);
    }

    private boolean checkListField(List<TemplateFieldReq> request) {
        for (TemplateFieldReq templateFieldReq : request) {
            if (!isValidField(templateFieldReq.getIdTemplateField(), templateFieldReq)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkListFieldEdit(List<TemplateFieldEditReq> request) {
        for (TemplateFieldEditReq templateFieldReq : request) {
            if (!isValidFieldEdit(templateFieldReq.getIdTemplateField(), templateFieldReq)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidFieldEdit(Long templateFieldId, TemplateFieldEditReq request) {
        boolean valid = true;
        Optional<TemplateField> templateField = templateFieldService.getTemplateFieldById(templateFieldId);
        if (!templateField.isPresent()) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Template Field không tồn tại");
        }
        String rules = templateField.get().getRules();
        String type = templateField.get().getType();
        if (type.equals("number")) {
            try {
                long valueRequest = Long.parseLong(request.getValue());
            } catch (NumberFormatException e) {
                throw new WebServiceException(HttpStatus.OK.value(), 409, "Dữ liệu không hợp lệ");
            }
        }
        if(rules != null && !rules.isEmpty()) {
            JSONObject jsonObject = (JSONObject) JSONValue.parse(rules);
            if(jsonObject.containsKey("required")) {
                boolean isRequired = (boolean) jsonObject.get("required");
                if (isRequired) {
                    if (request.getValue() != null && !request.getValue().trim().isEmpty()) {
                        if (jsonObject.containsKey("max")) {
                            String json = jsonObject.get("max").toString();
                            int max = Integer.parseInt(json);
                            if (request.getValue().trim().length() <= max) {
                                valid = true;
                            } else {
                                throw new WebServiceException(HttpStatus.OK.value(), 409, templateField.get().getName() + " không được vượt quá " + max + " ký tự");
                            }
                        }
                        if (jsonObject.containsKey("greaterThan")) {
                            JSONObject greaterThan = (JSONObject) jsonObject.get("greaterThan");
                            BigDecimal target = new BigDecimal(String.valueOf(greaterThan.get("target")));
                            if (type.equals("number")) {
                                BigDecimal value = new BigDecimal(request.getValue().trim());
                                if (value.compareTo(target) > 0) {
                                    valid = true;
                                } else {
                                    throw new WebServiceException(HttpStatus.OK.value(), 409, templateField.get().getName() + " phải lớn hơn " + target);
                                }
                            }
                        }
                        if (jsonObject.containsKey("lessThanOrEqual")) {
                            JSONObject lessThan = (JSONObject) jsonObject.get("lessThanOrEqual");
                            BigDecimal target = new BigDecimal(String.valueOf(lessThan.get("target")));
                            if (type.equals("number")) {
                                BigDecimal value = new BigDecimal(request.getValue().trim());
                                if (value.compareTo(target) <= 0) {
                                    valid = true;
                                } else {
                                    throw new WebServiceException(HttpStatus.OK.value(), 409, templateField.get().getName() + " phải nhỏ hơn hoặc bằng " + target);
                                }
                            }
                        }
                    } else {
                        throw new WebServiceException(HttpStatus.OK.value(), templateField.get().getName() + " không được để trống");
                    }
                }
            }
        }
        return valid;
    }

    private boolean isValidField(Long templateFieldId, TemplateFieldReq request) {
        boolean valid = true;
        Optional<TemplateField> templateField = templateFieldService.getTemplateFieldById(templateFieldId);
        if (!templateField.isPresent()) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Template Field không tồn tại");
        }
        String rules = templateField.get().getRules();
        String type = templateField.get().getType();
        if (type.equals("number")) {
            try {
                BigDecimal valueRequest = new BigDecimal(request.getValue());
            } catch (NumberFormatException e) {
                throw new WebServiceException(HttpStatus.OK.value(), 409, "Dữ liệu không hợp lệ");
            }
        }
        if(rules != null && !rules.isEmpty()) {
            JSONObject jsonObject = (JSONObject) JSONValue.parse(rules);
            if(jsonObject.containsKey("required")) {
                boolean isRequired = (boolean) jsonObject.get("required");
                if (isRequired) {
                    if (request.getValue() != null && !request.getValue().trim().isEmpty()) {
                        if (jsonObject.containsKey("max")) {
                            String json = jsonObject.get("max").toString();
                            int max = Integer.parseInt(json);
                            if (request.getValue().trim().length() <= max) {
                                valid = true;
                            } else {
                                throw new WebServiceException(HttpStatus.OK.value(), 409, templateField.get().getName() + " không được vượt quá " + max + " ký tự");
                            }
                        }
                        if (jsonObject.containsKey("greaterThan")) {
                            JSONObject greaterThan = (JSONObject) jsonObject.get("greaterThan");
                            BigDecimal target = new BigDecimal(String.valueOf(greaterThan.get("target")));
                            if (type.equals("number")) {
                                BigDecimal value = new BigDecimal(request.getValue().trim());
                                if (value.compareTo(target) > 0) {
                                    valid = true;
                                } else {
                                    throw new WebServiceException(HttpStatus.OK.value(), 409, templateField.get().getName() + " phải lớn hơn " + target);
                                }
                            }
                        }
                        if (jsonObject.containsKey("lessThanOrEqual")) {
                            JSONObject lessThan = (JSONObject) jsonObject.get("lessThanOrEqual");
                            BigDecimal target = new BigDecimal(String.valueOf(lessThan.get("target")));
                            if (type.equals("number")) {
                                BigDecimal value = new BigDecimal(request.getValue().trim());
                                if (value.compareTo(target) <= 0) {
                                    valid = true;
                                } else {
                                    throw new WebServiceException(HttpStatus.OK.value(), 409, templateField.get().getName() + " phải nhỏ hơn hoặc bằng " + target);
                                }
                            }
                        }
                    } else {
                        throw new WebServiceException(HttpStatus.OK.value(), templateField.get().getName() + " không được để trống");
                    }
                }
            }
        }
        return valid;
    }
}
