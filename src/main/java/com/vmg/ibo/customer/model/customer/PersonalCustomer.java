package com.vmg.ibo.customer.model.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalCustomer {
    @NotNull(message = "Số CCCD bắt buộc nhập!")
    private String cinumber;

    @NotNull(message = "Họ và tên bắt buộc nhập!")
    private String name;

    @NotNull(message = "Sđt bắt buộc nhập!")
    private String phone;

    private String address;
    @NotNull(message = "Quy mô vốn bắt buộc nhập!")
    private Long capitalSize;
    private String description;
}
