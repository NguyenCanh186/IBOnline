package com.vmg.ibo.core.model.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalCustomer {
    private Long id;
    @NotNull(message = "Số CCCD bắt buộc nhập!")
    private String CINumber;

    @NotNull(message = "Họ và tên bắt buộc nhập!")
    private String name;

    @NotNull(message = "Sđt bắt buộc nhập!")
    private String phone;

    private String address;

    @NotNull(message = "Email bắt buộc nhập!")
    private String email;

    private String description;
}
