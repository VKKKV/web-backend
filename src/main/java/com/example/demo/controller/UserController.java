package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.result.Result;
import com.example.demo.common.utils.LoginUserHolder;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理模块", description = "用户注册、登录、信息查询")
public class UserController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<?> register(@RequestBody UserDTO userDTO) {
        Integer userId = usersService.registerUser(userDTO);
        return Result.success(userId);
    }

//    @PostMapping("/login")
//    @Operation(summary = "用户登录")
//    public Result<LoginResult> login(@RequestBody LoginRequest request) {
//        String token = usersService.login(request.getUsername(), request.getPassword());
//        return Result.success(new LoginResult(token, LoginUserHolder.getCurrentUser().getUserId()));
//    }

    @GetMapping("/{userId}")
    @Operation(summary = "获取用户信息")
    public Result<Users> getUserInfo(@PathVariable Integer userId) {
        Users user = new Users();
        user.setUserId(userId);
        user.setUsername("test");
        user.setPasswordHash("test");
        user.setEmail("test@test.com");
        user.setPhone("1234567890");
        return Result.success(user);
    }

//    @PutMapping("/update-password")
//    @Operation(summary = "修改密码")
//    public Result<?> updatePassword(@RequestBody PasswordUpdateRequest request) {
//        usersService.updatePassword(
//                LoginUserHolder.getCurrentUser().getUserId(),
//                request.getOldPassword(),
//                request.getNewPassword());
//        return Result.success();
//    }
}
