package com.vmg.ibo.form.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FormSuggestDTO {
    private Long id;
    private Date createdAt;
    private TemplateDTO template;
    @JsonIgnore
    private List<FormFieldDTO> formFieldList;

    public List<FormFieldDTO> getFormFields() {
        if (template != null && template.getId() != null) {
            if(template.getId() == 1) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 3)
                        .collect(Collectors.toList());
            } else if(template.getId() == 2){
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 8)
                        .collect(Collectors.toList());
            } else if(template.getId() == 3){
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 12)
                        .collect(Collectors.toList());
            } else if(template.getId() == 4) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 17)
                        .collect(Collectors.toList());
            } else if(template.getId() == 5) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 21)
                        .collect(Collectors.toList());
            } else if(template.getId() == 6) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 26)
                        .collect(Collectors.toList());
            } else if(template.getId() == 7) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 30)
                        .collect(Collectors.toList());
            } else if(template.getId() == 8) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 36)
                        .collect(Collectors.toList());
            } else if(template.getId() == 9) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 44)
                        .collect(Collectors.toList());
            } else if(template.getId() == 10) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 51)
                        .collect(Collectors.toList());
            } else if(template.getId() == 11) {
                formFieldList.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId() == 55)
                        .collect(Collectors.toList());
            }

        }
        return null; // or an empty list, depending on your requirement
    }
}
