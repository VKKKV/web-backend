package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.example.demo.entity.User;
import com.example.demo.service.UserSerive;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserSerive userService;

    @Test
    void testGetById() {
//        User user = userService.getById(1);
        System.out.println(userService.getById(1));
    }

    @Test
    void testSaveOrUpdate() {
        User user1 = userService.getById(1);
        user1.setUsername("test1");

        User user2 = new User();

        user2.setUsername("test2");
        user2.setPassword("test");
        user2.setEmail("test@test.com");
        user2.setPhone("1234567890");

        userService.saveOrUpdate(user1);
        userService.saveOrUpdate(user2);
    }

    @Test
    void testUpdateById() {
        User user = userService.getById(1);
        user.setUsername("test3");
        userService.updateById(user);
    }

    @Test
    void testSaveBatch() {
        User user1 = new User();
        user1.setUsername("test4");
        user1.setPassword("test");
        user1.setEmail("test4@test.com");
        user1.setPhone("1234567890");

        User user2 = new User();
        user2.setUsername("test5");
        user2.setPassword("test");
        user2.setEmail("test5@test.com");
        user2.setPhone("1234567890");

        userService.saveBatch(Arrays.asList(user1, user2));
    }
    @Test
    void testSave() {
        User user = new User();
        user.setUsername("test6");
        user.setPassword("test");
        user.setEmail("test6@test.com");
        user.setPhone("1234567890");

        userService.save(user);
    }

}