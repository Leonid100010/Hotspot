package com.nju.edu.cn.service.cache;

import java.util.Map;

/**
 * @author: hwang
 * @date: 2023/8/17 23:52
 * @description: redis操作类
 */
public interface RedisService {

     /**
      * 是否存在 key
      * @param key
      * @return true/false
      */
     boolean hasKey(String key);

     /**
      * 加入一个value类型为map的<k, v>
      * @param key key
      * @param map map value
      */
     void putMap(String key, Map<String, Object> map);

     /**
      * 加入一个String类型的<k, v>
      * @param key key
      * @param value String value
      * @param timeout 过期时间
      */
     void putString(String key, String value, long timeout);

     /**
      * 加入一个String类型的<k, v>, 无过期时间
      * @param key key
      * @param value value
      */
     void putString(String key, String value);

     /**
      * 根据key获取其value
      * @param key
      * @return map value
      */
     Map<Object, Object> getMap(String key);

     /**
      * 根据key获取其value
      * @param key
      * @return string value
      */
     String getString(String key);

     /**
      * 设置某一个key的过期时间
      * @param key
      * @param time
      */
     void expire(String key, long time);

     /**
      * 获取某个key的过期时间
      * @param key
      * @return 过期时间
      */
     Long getExpire(String key);
}
