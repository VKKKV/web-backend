package com.example.demo.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testSet() {
        redisTemplate.opsForValue().set("name", "test");
    }

    @Test
    void testGet() {
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    void testDelete() {
        redisTemplate.delete("name");
    }

}


//@SpringBootTest
//public class RedisTemplateTest {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Test
//    void testSet() {
//        redisTemplate.opsForValue().set("name", "test");
//    }
//    @Test
//    void testGet() {
//        System.out.println(redisTemplate.opsForValue().get("name"));
//    }
//    @Test
//    void testDelete() {
//        redisTemplate.delete("name");
//    }
//
//}
