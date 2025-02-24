package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

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
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUsername, "test")
        ));
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setUsername("test1");
        user.setPassword("123");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        if (userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, "test1")) == 0) {
            userMapper.insert(user);
        }
    }

    @Test
    void testUpdateById() {
        User user = userMapper.selectById(1);
        user.setUsername("test1");
        userMapper.updateById(user);
    }

    @Test
    void testLogicDeleteById() {
        User user = userMapper.selectById(1);
        user.setIsDeleted(1);
        userMapper.updateById(user);
    }

}
