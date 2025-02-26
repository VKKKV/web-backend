package com.example.demo.page;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Users;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PageTest {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersService usersService;

    @Test
    void testPageService() {
        IPage<Users> page = usersService.page(new Page<>(1, 10));
        usersService.page(page).getRecords().forEach(System.out::println);
    }

    @Test
    void testPageMapper() {
        IPage<Users> page = new Page<>(2, 5);
        usersMapper.selectPage(page, null).getRecords().forEach(System.out::println);
    }

    @Test
    void testPageWithLambda() {
        IPage<Users> page = new Page<>(1, 5);
        usersService.page(page, new LambdaQueryWrapper<Users>().eq(Users::getUsername, "test")).getRecords().forEach(System.out::println);
    }

    @Test
    void testCustomPage() {
        IPage<Users> page = new Page<>(1, 5);
        usersMapper.selectUserPage(page).getRecords().forEach(System.out::println);
    }
}

