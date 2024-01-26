package com.vmg.ibo.contact_form.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ContactFormDTO {
    @NotNull(message = "Họ tên không được để trống!")
    @Size(max = 150, message = "Họ tên không được vượt quá 150 ký tự")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Họ tên chỉ chứa ký tự chữ cái")
    private String fullname;
    @NotNull(message = "Số điện thoại không được để trống!")
    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 ký tự")
    @Pattern(regexp = "^[0-9]+$", message = "Số điện thoại chỉ chứa ký tự số")
    private String phone;
    @Email(message = "Email không đúng định dạng!")
    @Size(max = 255, message = "Email không được vượt quá 255 ký tự")
    private String email;
    @Size(max = 500, message = "Nội dung yêu cầu không được vượt quá 500 ký tự")
    private String description;
}
