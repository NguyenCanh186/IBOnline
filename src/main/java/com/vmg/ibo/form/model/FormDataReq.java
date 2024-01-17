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
public class FormDataReq {
    private Long idTemplate;
    private List<TemplateFieldReq> templateFieldReqs;
}
