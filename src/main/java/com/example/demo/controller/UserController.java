package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.result.Result;
import com.example.demo.common.result.ResultCodeEnum;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import com.example.demo.vo.LoginRequestVO;
import com.example.demo.vo.LoginResponseVO;
import com.example.demo.vo.PasswordUpdateVO;
import com.example.demo.vo.UserInfoVO;
import com.example.demo.vo.RegisterRequestVO;
import com.example.demo.common.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理模块", description = "用户注册、登录、信息查询")
public class UserController {
    @Autowired
    private UsersService usersService;

    /**
     * 用户注册
     *
     * @param request 注册信息
     * @return 用户ID
     */
    @Operation(summary = "用户注册", description = "创建新用户账号")
    @PostMapping("/register")
    public Result<?> register(
            @Parameter(description = "注册信息", required = true)
            @RequestBody RegisterRequestVO request) {

        // 调用服务层注册用户
        Integer userId = usersService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getPhone()
        );

        return Result.success(userId);
    }

    /**
     * 用户登录
     *
     * @param request 登录信息
     * @return 登录结果，包含token等信息
     */
    @Operation(summary = "用户登录", description = "验证用户身份并返回访问令牌")
    @PostMapping("/login")
    public Result<LoginResponseVO> login(
            @Parameter(description = "登录信息", required = true)
            @RequestBody LoginRequestVO request) {

        // 调用服务层登录
        String token = usersService.login(request.getUsername(), request.getPassword());

        // 构建登录响应
        LoginResponseVO response = LoginResponseVO.builder()
                .token(token)
                .userId(getUserIdFromToken(token))
                .username(request.getUsername())
                .expiresIn(3600) // 令牌过期时间，单位秒
                .build();

        return Result.success(response);
    }

    /**
     * 修改密码
     *
     * @param passwordUpdate 密码更新信息
     * @return 操作结果
     */
    @Operation(summary = "修改密码", description = "更新用户密码")
    @PutMapping("/update-password")
    public Result<?> updatePassword(
            @Parameter(description = "密码更新信息", required = true)
            @RequestBody PasswordUpdateVO passwordUpdate) {

        // 调用服务层修改密码
        usersService.updatePassword(
                passwordUpdate.getUserId(),
                passwordUpdate.getOldPassword(),
                passwordUpdate.getNewPassword()
        );

        return Result.success();
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户详细信息
     */
    @Operation(summary = "获取用户信息", description = "根据用户ID查询详细信息")
    @GetMapping("/{userId}")
    public Result<UserInfoVO> getUserInfo(
            @Parameter(description = "用户ID", example = "12345", required = true)
            @PathVariable Integer userId) {

        // 查询用户信息
        Users user = usersService.getById(userId);

        if (user == null || user.getIsDeleted() == 1) {
            return Result.fail(ResultCodeEnum.USER_NOT_EXIST);
        }

        // 构建用户信息响应
        UserInfoVO userInfo = UserInfoVO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .registerTime(user.getCreatedAt())
                .balance(user.getBalance())
                .build();

        return Result.success(userInfo);
    }

    /**
     * 从令牌中获取用户ID
     *
     * @param token JWT令牌
     * @return 用户ID
     */
    private Integer getUserIdFromToken(String token) {
        // 使用JwtUtil解析token获取用户ID
        return JwtUtil.getUserIdFromToken(token);
    }
}
