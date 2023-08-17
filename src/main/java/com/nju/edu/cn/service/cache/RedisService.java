package com.nju.edu.cn.service.cache;

import java.util.Map;

/**
 * @author: hwang
 * @date: 2023/8/17 23:52
 * @description: redis操作类
 */
public interface RedisService {


     boolean hasKey(String key);

     void putMap(String key, Map<String, Object> map);

     void putString(String key, String value, long timeout);

     Map<Object, Object> getMap(String key);

     String getString(String key);

     void expire(String key, long time);

     Long getExpire(String key);
}
