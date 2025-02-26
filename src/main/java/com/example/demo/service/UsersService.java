package com.example.demo.service;

import com.example.demo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author Kita
* @description 针对表【users】的数据库操作Service
* @createDate 2025-02-24 16:42:24
*/
public interface UsersService extends IService<Users> {

    String login(UserDTO userDTO);

    UserDTO getUserInfoById(Integer userId);

}
