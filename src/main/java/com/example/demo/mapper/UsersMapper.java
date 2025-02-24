package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Kita
* @description 针对表【users】的数据库操作Mapper
* @createDate 2025-02-24 16:42:24
* @Entity com.example.demo.entity.Users
*/
public interface UsersMapper extends BaseMapper<Users> {
    IPage<Users> selectUserPage(IPage<Users> page);
}




