package com.example.demo.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserSerive;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserSerive userSerive;

    @Test
    void testLambdaQueryWrapper () {
        //search by username
        userSerive.list(new LambdaQueryWrapper<User>().eq(User::getUsername, "test")).forEach(System.out::println);

        //search by email
        userSerive.list(new LambdaQueryWrapper<User>().eq(User::getEmail, "test@example.com")).forEach(System.out::println);

        //search by created_at between 2022-01-01 and 2022-12-31
        userSerive.list(new LambdaQueryWrapper<User>().between(User::getCreatedAt, "2022-01-01 00:00:00", "2022-12-31 23:59:59")).forEach(System.out::println);
    }


    @Test
    void testLambdaUpdateWrapper() {
        //update username to test1 where username is test
        userSerive.update(
                new LambdaUpdateWrapper<User>()
                        .eq(User::getUsername, "test")
                        .set(User::getUsername, "test1")
        );
    }


}

