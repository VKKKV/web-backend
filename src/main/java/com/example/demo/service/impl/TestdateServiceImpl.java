package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Testdate;
import com.example.demo.service.TestdateService;
import com.example.demo.mapper.TestdateMapper;
import org.springframework.stereotype.Service;

/**
* @author Kita
* @description 针对表【testDate】的数据库操作Service实现
* @createDate 2025-02-24 16:42:24
*/
@Service
public class TestdateServiceImpl extends ServiceImpl<TestdateMapper, Testdate>
    implements TestdateService{

}




