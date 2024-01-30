package com.vmg.ibo.form.dto;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class FormSuggestDTO {
    private Long id;
    private Date createdAt;
    private TemplateDTO template;
    private List<FormFieldDTO> formFields;

    private static final Map<Long, Long> TEMPLATE_FIELD_ID_MAP = createTemplateFieldIdMap();

    private static Map<Long, Long> createTemplateFieldIdMap() {
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, 3L);
        map.put(2L, 8L);
        map.put(3L, 12L);
        map.put(4L, 17L);
        map.put(5L, 21L);
        map.put(6L, 26L);
        map.put(7L, 30L);
        map.put(8L, 36L);
        map.put(9L, 44L);
        map.put(10L, 51L);
        map.put(11L, 55L);
        return Collections.unmodifiableMap(map);
    }

    public List<FormFieldDTO> getFormFields() {
        if (template != null && template.getId() != null) {
            Long templateFieldId = TEMPLATE_FIELD_ID_MAP.get(template.getId());
            if (templateFieldId != null) {
                return formFields.stream()
                        .filter(field -> field.getTemplateFieldId() != null && field.getTemplateFieldId().equals(templateFieldId))
                        .collect(Collectors.toList());
            }
        }

        return Collections.emptyList(); // Return an empty list if template or template ID is not found
    }
}
