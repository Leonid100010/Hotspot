package com.nju.edu.cn.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void stringTest(){
        stringRedisTemplate.opsForValue().set("testByWff", "1");
    }
}
