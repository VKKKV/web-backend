package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.SecurityEvents;
import com.example.demo.service.SecurityEventsService;
import com.example.demo.mapper.SecurityEventsMapper;
import org.springframework.stereotype.Service;

/**
* @author Kita
* @description 针对表【security_events】的数据库操作Service实现
* @createDate 2025-02-24 16:42:24
*/
@Service
public class SecurityEventsServiceImpl extends ServiceImpl<SecurityEventsMapper, SecurityEvents>
    implements SecurityEventsService{

}




