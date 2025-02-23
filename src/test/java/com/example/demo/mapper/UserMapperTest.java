package com.example.demo.mapper;

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

    // pending
    @Test
    void testInsert() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        // 避免 username 重复
        if (userMapper.selectByUsername("test")) userMapper.insert(user);
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
