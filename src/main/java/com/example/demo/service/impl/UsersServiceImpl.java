package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import com.example.demo.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author Kita
* @description 针对表【users】的数据库操作Service实现
* @createDate 2025-02-24 16:42:24
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




