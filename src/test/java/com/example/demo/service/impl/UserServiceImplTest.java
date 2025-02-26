package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UsersService userService;

    @Test
    void testGetById() {
        System.out.println(userService.getById(1));
    }

//    @Test
//    void testSaveOrUpdate() {
//        Users user1 = userService.getById(1);
//        user1.setUsername("test1");
//
//        Users user2 = new Users();
//        user2.setUsername("test7");
//        user2.setPasswordHash("test");
//        user2.setEmail("test7@test.com");
//        user2.setPhone("1234567890");
//
//        userService.saveOrUpdate(user1);
//        userService.saveOrUpdate(user2);
//    }

    @Test
    void testUpdateById() {
        Users user = userService.getById(1);
        user.setUsername("test3");
        userService.updateById(user);
    }

//    @Test
//    void testSaveBatch() {
//        Users user1 = new Users();
//        user1.setUsername("test4");
//        user1.setPasswordHash("test");
//        user1.setEmail("test4@test.com");
//        user1.setPhone("1234567890");
//
//
//        Users user2 = new Users();
//        user2.setUsername("test5");
//        user2.setPasswordHash("test");
//        user2.setEmail("test5@test.com");
//        user2.setPhone("1234567890");
//
//        userService.saveBatch(Arrays.asList(user1, user2));
//    }

    @Test
    void testSave() {
        Users user = new Users();
        user.setUsername("test1");
        user.setPasswordHash("test");
        user.setEmail("test6@test.com");
        user.setPhone("1234567890");

        if (userService.count(new LambdaQueryWrapper<Users>().eq(Users::getUsername, "test1")) == 0) {
            userService.save(user);
        }
    }

}