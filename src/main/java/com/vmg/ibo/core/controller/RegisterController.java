package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.customer.RegisterModel;
import com.vmg.ibo.core.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/register")
@Slf4j
public class RegisterController {
    @Autowired
    private IUserService userService;

    @PostMapping("/create-user")
    public Result<?> createUser(@Validated(Insert.class) @RequestBody RegisterModel registerModel) {
        if (!userService.isValidEmail(registerModel.getEmail())) {
            return Result.error(409, "Email không đúng định dạng hoặc đã được đăng ký");
        }
        userService.registerUser(registerModel);
        return Result.success("Đã gửi email xác nhận đến " + registerModel.getEmail() + " vui lòng kiểm tra email để tiếp tục đăng ký");
    }
}
