package com.example.demo.page;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserSerive;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PageTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSerive userSerive;

    @Test
    void testPageService() {
        IPage<User> page = userSerive.page(new Page<>(1, 10));
        userSerive.page(page).getRecords().forEach(System.out::println);
    }

    @Test
    void testPageMapper() {
        IPage<User> page = new Page<>(2, 5);
        userMapper.selectPage(page, null).getRecords().forEach(System.out::println);
    }

    @Test
    void testPageWithLambda() {
        IPage<User> page = new Page<>(1, 5);
        userSerive.page(page, new LambdaQueryWrapper<User>().eq(User::getUsername, "test")).getRecords().forEach(System.out::println);
    }

    @Test
    void testCustomPage() {
        IPage<User> page = new Page<>(1, 5);
        userMapper.selectUserPage(page).getRecords().forEach(System.out::println);
    }
}

