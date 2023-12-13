package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/register")
@Slf4j
public class RegisterControler {
    @Autowired
    private IUserService userService;

    @GetMapping("/create-user")
    public Result<?> createUser(@RequestParam String email) {
        return Result.success(userService.registerUser(email));
    }
}
