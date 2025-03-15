package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.exception.TradeException;
import com.example.demo.common.result.ResultCodeEnum;
import com.example.demo.common.utils.JwtUtil;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public String login(UserDTO userDTO) {
        //1.判断手机号码和验证码是否为空
        if (!StringUtils.hasText(userDTO.getPhone())) {
            throw new TradeException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);//号码为空异常。
        }

        //2.判断用户是否存在,不存在则注册（创建用户）
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getPhone, userDTO.getPhone());
        Users user = usersMapper.selectOne(queryWrapper);
        //根据手机号查询用户，我们这里的设计是手机号为账号名
        if (user == null) {//没有这个用户，则注册用户。
            user = new Users();
            user.setUsername("test1");
            user.setPasswordHash("test");
            user.setEmail("test6@test.com");
            user.setPhone(userDTO.getPhone());

            usersMapper.insert(user);
        }

        //5.创建并返回TOKEN
        return JwtUtil.createToken(user.getUserId(), userDTO.getPhone());//调用工具类。
    }

    @Override
    public Integer registerUser(UserDTO userDTO) {
        return 0;
    }
}




