package com.nju.edu.cn.service.cache.impl;

import com.nju.edu.cn.service.cache.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: hwang
 * @date: 2023/8/17 23:53
 * @description:
 */
@Service
public class RedisServiceImpl implements RedisService {

    private static final TimeUnit timeUnit = TimeUnit.MINUTES;

    @Resource
    StringRedisTemplate stringRedisTemplate;


    public boolean hasKey(String key){
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    public void putMap(String key, Map<String, Object> map){
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    public void putString(String key, String value, long timeout){
        stringRedisTemplate.opsForValue().set(key,
                value, timeout, timeUnit);
    }

    public Map<Object, Object> getMap(String key){
        return stringRedisTemplate.opsForHash().entries(key);
    }

    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void expire(String key, long time){
        stringRedisTemplate.expire(key, time, timeUnit);
    }

    public Long getExpire(String key){
        return stringRedisTemplate.getExpire(key);
    }
}
