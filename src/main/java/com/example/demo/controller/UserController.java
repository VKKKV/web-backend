package com.example.demo.controller;

import com.example.demo.common.result.Result;
import com.example.demo.common.utils.LoginUserHolder;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import com.example.demo.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理模块", description = "用户注册、登录、信息查询") // Knife4j 分组标题
public class UserController {

    @GetMapping("getById")
    @Operation(summary = "获取用户信息")
    public Users getById(@RequestParam("userId") Integer userId) {
        Users user = new Users();
        user.setUserId(userId);
        user.setUsername("test");
        user.setPasswordHash("test");
        user.setEmail("test@test.com");
        user.setPhone("1234567890");
        return user;
    }


//    @Autowired
//    private UsersService usersService;

    // todo
//    @PostMapping("login")
//    @Operation(summary = "用户登录", description = "返回 JWT Token 用于后续接口鉴权")
//    public Result<String> login(@RequestBody UserDTO userDTO) {
//        //判断是否登录成功，并返回jwt token
//        String token = usersService.login(userDTO);
//        return Result.ok(token);
//    }

    // todo
//    @GetMapping("info")
//    @Operation(summary = "获取登录用户信息")
//    public Result<UserDTO> info() {
//        //根据threadlocal中userId，获取用户信息UserInfoVo
//        UserDTO info =
//                usersService.getUserInfoById(LoginUserHolder.getLoginUser().getUserId());
//        return Result.ok(info);
//    }
}