package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.exception.TradeException;
import com.example.demo.common.result.ResultCodeEnum;
import com.example.demo.common.utils.JwtUtil;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import com.example.demo.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author Kita
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2025-02-24 16:42:24
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 判断用户是否存在，不存在则注册，存在则登录返回jwt-token
     */
    @Override
    public String login(String username, String password) {
        // 1. 验证用户名和密码是否为空
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new TradeException(ResultCodeEnum.PARAM_ERROR);
        }

        // 2. 根据用户名查询用户
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, username)
                .eq(Users::getIsDeleted, 0); // 只查询未删除的用户
        Users user = usersMapper.selectOne(queryWrapper);

        // 3. 验证用户是否存在
        if (user == null) {
            throw new TradeException(ResultCodeEnum.USER_NOT_EXIST);
        }

        // 4. 验证密码是否正确
        if (!password.equals(user.getPassword())) {
            throw new TradeException(ResultCodeEnum.PASSWORD_ERROR);
        }

        // 5. 创建并返回JWT令牌
        return JwtUtil.createToken(user.getUserId(), user.getUsername());
    }

    /**
     * 注册新用户
     */
    @Override
    public Integer registerUser(String username, String password, String email, String phone) {
        // 1. 验证参数
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new TradeException(ResultCodeEnum.PARAM_ERROR);
        }

        // 2. 检查用户名是否已存在
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, username)
                .eq(Users::getIsDeleted, 0); // 只查询未删除的用户
        Users existUser = usersMapper.selectOne(queryWrapper);

        if (existUser != null) {
            throw new TradeException(ResultCodeEnum.USERNAME_EXIST);
        }

        // 3. 创建新用户
        Users user = new Users();
        user.setUsername(username);
        // 使用BCrypt加密密码
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setIsDeleted(0); // 设置为未删除状态

        // 4. 保存用户
        usersMapper.insert(user);

        return user.getUserId();
    }

    /**
     * 修改密码
     */
    @Override
    public void updatePassword(Integer userId, String oldPassword, String newPassword) {
        // 1. 验证参数
        if (userId == null || !StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            throw new TradeException(ResultCodeEnum.PARAM_ERROR);
        }

        // 2. 查询用户
        Users user = usersMapper.selectById(userId);
        if (user == null || user.getIsDeleted() == 1) {
            throw new TradeException(ResultCodeEnum.USER_NOT_EXIST);
        }

        // 3. 验证旧密码
        if (!oldPassword.equals(user.getPassword())) {
            throw new TradeException(ResultCodeEnum.PASSWORD_ERROR);
        }

        // 4. 更新密码
        user.setPassword(newPassword);
        user.setUpdatedAt(new Date());

        usersMapper.updateById(user);
    }
}




