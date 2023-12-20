package com.vmg.ibo.customer.controller;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.customer.model.customer.RegisterModel;
import com.vmg.ibo.customer.model.CodeAndEmail;
import com.vmg.ibo.customer.model.ForgotPass;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.customer.service.code_and_email.ICodeAndEmailService;
import com.vmg.ibo.core.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/register")
@Slf4j
public class RegisterController {
    @Autowired
    private IUserService userService;

    @Autowired
    private ICodeAndEmailService codeAndEmailService;

    @PostMapping("/create-user")
    public Result<?> createUser(@Validated(Insert.class) @RequestBody RegisterModel registerModel) {
        if (!userService.isValidEmail(registerModel.getEmail())) {
            return Result.error(409, "Email không đúng định dạng hoặc đã được đăng ký");
        }
        if (!Objects.equals(registerModel.getPassword(), registerModel.getConfirmPassword())) {
            return Result.error(409, "Mật khẩu không khớp");
        }
        userService.registerUser(registerModel);
        return Result.success("Đã gửi email xác nhận đến " + registerModel.getEmail() + " vui lòng kiểm tra email để tiếp tục đăng ký");
    }

    @GetMapping
    public Result<?> active(@RequestParam String code) {
        CodeAndEmail codeAndEmail = codeAndEmailService.findByCode(code);
        if (codeAndEmail == null) {
            return Result.error(409, "Mã xác nhận không hợp lệ");
        }
        User user = userService.findByEmail(codeAndEmail.getEmail());
        user.setActive(true);
        userService.activeUser(user);
        codeAndEmailService.deleteCodeAndEmail(codeAndEmail.getId());
        return Result.success("Đã kích hoạt tài khoản thành công");
    }

    @GetMapping("/forgot-password")
    public Result<?> forgotPassword(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return Result.error(409, "Email không tồn tại");
        }
        if (userService.isExistEmail(email)) {
            return Result.error(409, "Chúng tôi đã gửi email đổi mật khẩu đến email này, vui lòng kiểm tra email");
        }
        user.setPassword(null);
        userService.activeUser(user);
        userService.forgotPassword(email);
        return Result.success("Đã gửi email xác nhận đến " + email + " vui lòng kiểm tra email để tiếp tục đổi mật khẩu");
    }

    @PostMapping("/change-pass")
    public Result<?> changePassword(@Validated(Insert.class) @RequestBody ForgotPass forgotPass) {
        if (!Objects.equals(forgotPass.getPassword(), forgotPass.getConfirmPassword())) {
            return Result.error(409, "Mật khẩu không khớp");
        }
        CodeAndEmail codeAndEmail = codeAndEmailService.findByCode(forgotPass.getCode());
        if (codeAndEmail == null) {
            return Result.error(409, "Mã xác nhận không hợp lệ");
        }
        User user = userService.findByEmail(codeAndEmail.getEmail());
        if (user == null) {
            return Result.error(409, "Email không tồn tại");
        }
        userService.changePasswordBeForgot(forgotPass);
        codeAndEmailService.deleteCodeAndEmail(codeAndEmail.getId());
        return Result.success("Đổi mật khẩu thành công");
    }
}
