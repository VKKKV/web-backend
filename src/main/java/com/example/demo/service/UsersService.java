package com.example.demo.service;

import com.example.demo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Kita
* @description 针对表【users】的数据库操作Service
* @createDate 2025-02-24 16:42:24
*/
public interface UsersService extends IService<Users> {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return JWT令牌
     */
    String login(String username, String password);

    /**
     * 注册用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param phone 手机号
     * @return 用户ID
     */
    Integer registerUser(String username, String password, String email, String phone);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Integer userId, String oldPassword, String newPassword);
}
