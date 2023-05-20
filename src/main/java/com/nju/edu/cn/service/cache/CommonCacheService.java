package com.nju.edu.cn.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CommonCacheService {

    private static final TimeUnit timeUnit = TimeUnit.MINUTES;

    StringRedisTemplate stringRedisTemplate;

    @Autowired
    public CommonCacheService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

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
