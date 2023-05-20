package com.nju.edu.cn.service.cache;

/**
 * redis中使用到的常量：包括key前缀，过期时间等
 */
public class RedisConstants {
    public static final String STATION_KEY = "cache:station:";

    public static final String STATION_ENTRY_LIST_KEY = "cache:station:entry:list:";

    public static final Long CACHE_STATION_TTL = 30L;

    public static final String REPLICA_1 = "replica1:";

    public static final String REPLICA_2 = "replica2";


}
