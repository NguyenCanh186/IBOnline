package com.vmg.ibo.customer.model.customer;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PersonalCustomer {

    @NotNull(message = "Số CCCD bắt buộc nhập!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Số CCCD không được để trống!", groups = {Insert.class, Update.class})
    @Size(max = 20, message = "Số CCCD không được vượt quá 20 ký tự!", groups = {Insert.class, Update.class})
    private String cinumber;

    @NotNull(message = "Họ và tên bắt buộc nhập!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Họ và tên không được để trống!", groups = {Insert.class, Update.class})
    @Size(max = 50, message = "Họ và tên không được vượt quá 50 ký tự!", groups = {Insert.class, Update.class})
    private String name;

    @NotNull(message = "Số điện thoại bắt buộc nhập!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Số điện thoại không được để trống!", groups = {Insert.class, Update.class})
    @Size(max = 11, message = "Số điện thoại không được vượt quá 11 ký tự!", groups = {Insert.class, Update.class})
    private String phone;
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự!", groups = {Insert.class, Update.class})
    private String address;

    @NotNull(message = "Quy mô vốn bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long capitalSize;
    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự!", groups = {Insert.class, Update.class})
    private String description;
}

