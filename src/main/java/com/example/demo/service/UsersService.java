package com.example.demo.service;

import com.example.demo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;

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

    /**
     * 获取用户余额
     * @param userId 用户ID
     * @return 用户余额
     */
    BigDecimal getUserBalance(Integer userId);

    /**
     * 更新用户余额
     * @param userId 用户ID
     * @param amountChange 余额变动金额（正数为增加，负数为减少）
     * @return 更新是否成功
     */
    boolean updateUserBalance(Integer userId, BigDecimal amountChange);

    /**
     * 检查用户余额是否充足
     * @param userId 用户ID
     * @param amountNeeded 需要的金额
     * @return 是否充足
     */
    boolean checkSufficientBalance(Integer userId, BigDecimal amountNeeded);
}
