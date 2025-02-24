package com.example.demo.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// ... existing代码...
public class WrapperTest {

    @Autowired
    private UsersService usersService; // 修正1：UserSerive→UsersService，变量名userSerive→usersService

    @Test
    void testLambdaQueryWrapper () {
        //search by username
        usersService.list(new LambdaQueryWrapper<Users>().eq(Users::getUsername, "test")).forEach(System.out::println); // 修正2：User→Users

        //search by email
        usersService.list(new LambdaQueryWrapper<Users>().eq(Users::getEmail, "test@example.com")).forEach(System.out::println); // 修正2

        //search by created_at between 2022-01-01 and 2022-12-31
        usersService.list(new LambdaQueryWrapper<Users>().between(Users::getCreatedAt, "2022-01-01 00:00:00", "2022-12-31 23:59:59")).forEach(System.out::println);
    }

    @Test
    void testLambdaUpdateWrapper() {
        //update username to test1 where username is test
        usersService.update( // 修正3：userSerive→usersService
                new LambdaUpdateWrapper<Users>() // 修正2：User→Users
                        .eq(Users::getUsername, "test")
                        .set(Users::getUsername, "test1")
        );
    }
}
