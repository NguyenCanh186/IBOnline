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
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        if (userService.isValidEmail(registerModel.getEmail()) == 1) {
            return Result.error(409, "Email đã tồn tại trong hệ thống");
        }
        if (userService.isValidEmail(registerModel.getEmail()) == 2) {
            return Result.error(409, "Email không đúng định dạng");
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
            return Result.error(400, "Email không tồn tại");
        }
        if (!user.isActive()) {
            return Result.error(400, "Vui lòng kích hoạt tài khoản trước khi đổi mật khẩu");
        }
        CodeAndEmail codeAndEmail = codeAndEmailService.findByEmail(email);
        if (codeAndEmail != null) {
            if (new Date().getTime() - codeAndEmail.getCreateAt().getTime() < 600000) {
                long timeDifference = 600000 - (new Date().getTime() - codeAndEmail.getCreateAt().getTime());
                long minutes = (timeDifference / 1000) / 60;
                long seconds = (timeDifference / 1000) % 60;
                String formattedTime = (minutes > 0 ? minutes + " phút " : "") + seconds + " giây";
                return Result.error(409, "Chúng tôi đã gửi link đổi mật khẩu đến email này, vui lòng thử lại sau " + formattedTime);
            }
            if (new Date().getTime() - codeAndEmail.getCreateAt().getTime() > 600000) {
                codeAndEmailService.deleteCodeAndEmail(codeAndEmail.getId());
            }
        }

        userService.forgotPassword(email);
        return Result.success("Đã gửi email xác nhận đến " + email + " vui lòng kiểm tra email để tiếp tục đổi mật khẩu");
    }

    @PostMapping("/change-pass")
    public Result<?> changePassword(@Validated(Insert.class) @RequestBody ForgotPass forgotPass) {
        if (forgotPass.getCode() == null || forgotPass.getCode().isEmpty()) {
            return Result.error(409, "Mã là bắt buộc");
        }
        if (!Objects.equals(forgotPass.getPassword(), forgotPass.getConfirmPassword())) {
            return Result.error(409, "Mật khẩu không khớp");
        }
        CodeAndEmail codeAndEmail = codeAndEmailService.findByCode(forgotPass.getCode());
        if (codeAndEmail == null) {
            return Result.error(409, "Mã không tồn tại hoặc đã được sử dụng");
        }
        if (new Date().getTime() - codeAndEmail.getCreateAt().getTime() > 600000) {
            return Result.error(409, "Mã xác nhận đã hết hiệu lực");
        }
        User user = userService.findByEmail(codeAndEmail.getEmail());
        if (user == null) {
            return Result.error(409, "Email không tồn tại");
        }
        userService.changePasswordBeForgot(forgotPass);
        codeAndEmailService.deleteCodeAndEmail(codeAndEmail.getId());
        return Result.success("Đổi mật khẩu thành công");
    }

    @GetMapping("/check-code")
    public Result<?> checkCode(@RequestParam String code) {
        if (code == null || code.isEmpty()) {
            return Result.error(400, "Mã là bắt buộc");
        }
        CodeAndEmail codeAndEmail = codeAndEmailService.findByCode(code);
        if (codeAndEmail == null) {
            return Result.error(400, "Mã không tồn tại hoặc đã được sử dụng");
        }
        if (new Date().getTime() - codeAndEmail.getCreateAt().getTime() > 600000) {
            return Result.error(400, "Mã xác nhận đã hết hiệu lực");
        }
        return Result.success("Mã xác nhận hợp lệ", HttpStatus.OK);
    }
}
