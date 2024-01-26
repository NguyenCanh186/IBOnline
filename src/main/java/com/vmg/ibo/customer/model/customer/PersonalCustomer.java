package com.vmg.ibo.customer.model.customer;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PersonalCustomer {

    @NotNull(message = "Số CCCD bắt buộc nhập!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Số CCCD không được để trống!", groups = {Insert.class, Update.class})
    @Size(max = 20, message = "Số CCCD không được vượt quá 20 ký tự!", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^[0-9]+$", message = "Số CCCD chỉ được chứa số", groups = {Insert.class, Update.class})
    private String cinumber;

    @NotNull(message = "Họ và tên bắt buộc nhập!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Họ và tên không được để trống!", groups = {Insert.class, Update.class})
    @Size(max = 255, message = "Họ và tên không được vượt quá 255 ký tự!", groups = {Insert.class, Update.class})
    private String name;

    @NotNull(message = "Số điện thoại bắt buộc nhập!", groups = {Insert.class, Update.class})
    @NotBlank(message = "Số điện thoại không được để trống!", groups = {Insert.class, Update.class})
    @Size(min = 10, max = 100, message = "Số điện thoại phải có ít nhất 10 ký tự và nhiều nhất 100 ký tự", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^[0-9]+$", message = "Số điện thoại chỉ được chứa số", groups = {Insert.class, Update.class})
    private String phone;
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự!", groups = {Insert.class, Update.class})
    private String address;

    @NotNull(message = "Quy mô vốn bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Min(value = 1, message = "Quy mô vốn phải lớn hơn 0")
    @Max(value = 100000000000L, message = "Quy mô vốn phải nhỏ hơn 100,000,000,000")
    private Long capitalSize;
    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự!", groups = {Insert.class, Update.class})
    private String description;
}

