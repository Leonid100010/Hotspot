package com.nju.edu.cn.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void stringTest(){

        stringRedisTemplate.opsForValue().set("testByWff", "1", 100, TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.opsForValue().getOperations().getExpire("testByWff"));
    }

}
