package com.vmg.ibo.form.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class FormDataEditReq {
    private Long idForm;
    private List<TemplateFieldEditReq> templateFieldReqs;
}
