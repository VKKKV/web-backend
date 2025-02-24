package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UsersMapper userMapper;

    @Test
    void testList() {
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    void testSelectById() {
        System.out.println(userMapper.selectById(1));
    }

    @Test
    void testSelectByUsername() {
        System.out.println(userMapper.selectList(
                Wrappers.<Users>lambdaQuery()
                        .eq(Users::getUsername, "test")
        ));
    }

    @Test
    void testInsert() {
        Users user = new Users();
        user.setUsername("test1");
        user.setPasswordHash("123");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        if (userMapper.selectCount(new LambdaQueryWrapper<Users>().eq(Users::getUsername, "test1")) == 0) {
            userMapper.insert(user);
        }
    }

    @Test
    void testUpdateById() {
        Users user = userMapper.selectById(1);
        user.setUsername("test1");
        if (userMapper.selectCount(new LambdaQueryWrapper<Users>().eq(Users::getUsername, "test1")) == 0) {
            userMapper.updateById(user);
        }

    }

    @Test
    void testLogicDeleteById() {
        Users user = userMapper.selectById(1);
        user.setIsDeleted(1);
        userMapper.updateById(user);
    }

}
