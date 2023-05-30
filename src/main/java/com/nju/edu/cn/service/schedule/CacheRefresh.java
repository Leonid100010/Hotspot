package com.nju.edu.cn.service.schedule;

import com.nju.edu.cn.service.HotspotService;
import com.nju.edu.cn.service.SourceHotData;
import com.nju.edu.cn.service.cache.CommonCacheService;
import com.nju.edu.cn.util.JsonUtil;
import com.nju.edu.cn.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.nju.edu.cn.service.cache.RedisConstants.*;

@Slf4j
@Service
public class CacheRefresh {

    CommonCacheService commonCacheService;


    HotspotService hotspotService;
    SourceHotData sourceHotData;

    /**
     * redis中map结构中的键
     */
    private static final String update_time = "updateTime";

    @Autowired
    public CacheRefresh(CommonCacheService commonCacheService, SourceHotData sourceHotData,
                        HotspotService hotspotService) {
        this.commonCacheService = commonCacheService;
        this.sourceHotData = sourceHotData;
        this.hotspotService = hotspotService;
    }

    public void refreshStationCache(String station){
        String key1 = STATION_KEY + REPLICA_1 + station;
        String key2 = STATION_KEY + REPLICA_2 + station;
        boolean hasRe1 =Boolean.TRUE.equals( commonCacheService.hasKey(key1));
        boolean hasRe2 = Boolean.TRUE.equals( commonCacheService.hasKey(key2));


        if( !hasRe1 && !hasRe2 ){
            //两份副本都不存在
            refresh(station);
        }else if(checkExpiring(station) == 1){
            //数据快过期了，提前更新
            refresh(station);
        }else if(checkUpdateTime(station) == 1){
            //数据已经旧了
            refresh(station);
        }else {
            //延长过期时间
            refreshExpire(station);
        }

    }

    /**
     * 更新缓存：获取新的数据，删除旧的缓存
     * @param station
     */
    private void refresh(String station){
        hotspotService.hotSpotRefresh(station);
    }

    /**
     * 检测缓存中数据的update_time是否已经过时
     * @param station
     * @return 1：已经过时，需要更新缓存；0：不需要；-1：报错
     */
    private int checkUpdateTime(String station){
        //获取源数据
        String currHotSpotData = sourceHotData.getHotDataByStation(station);
        String currTime = JsonUtil.strToJson(currHotSpotData).getString("update_time");
        String cacheTime ;

        Map<Object, Object> map1 = commonCacheService.getMap(STATION_KEY + REPLICA_1 + station);
        Map<Object, Object> map2 = commonCacheService.getMap(STATION_KEY + REPLICA_2 + station);


        if(!map1.isEmpty() && !map2.isEmpty()){
            cacheTime = TimeUtil.compare((String) map1.get(update_time), (String) map2.get(update_time)) == 1? (String) map2.get(update_time): (String) map1.get(update_time);
        }else if(!map1.isEmpty()){
            cacheTime = (String) map1.get(update_time);
        }else if(!map2.isEmpty()){
            cacheTime = (String) map2.get(update_time);
        }else{
            log.error("Both replicas are out of time. ");
            return -1;
        }
        log.info("cacheTime:" + cacheTime);
        log.info("currTime: " + currTime);
        return TimeUtil.compare(cacheTime, currTime) == 1? 1: 0;

    }


    /**
     * 检测缓存是否即将过期
     * @param station
     * @return
     */
    private int checkExpiring(String station){
        Long leftTime1 = commonCacheService.getExpire(STATION_KEY + REPLICA_1 + station);
        Long leftTime2 = commonCacheService.getExpire(STATION_KEY + REPLICA_2 + station);

        if(leftTime1 != -2 && leftTime2 != -2){
            //如果两份副本都快过期
            if(leftTime1 < 20 && leftTime2 < 20){
                return 1;
            }else return 0;
        }else if(leftTime1 != -2){
            return leftTime1 < 20? 1:0;
        }else if(leftTime2 != -2){
            return leftTime2 < 20? 1:0;
        }else{
            log.error("Both replicas are out of time. ");
            return -1;
        }
    }

    /**
     * 刷新key的过期时间
     * @param station
     */
    private void refreshExpire(String station){
        if(commonCacheService.hasKey(STATION_KEY + REPLICA_1 + station)){
            commonCacheService.expire(STATION_KEY + REPLICA_1 + station, CACHE_STATION_TTL);
        }
        if(commonCacheService.hasKey(STATION_KEY + REPLICA_2 + station)){
            commonCacheService.expire(STATION_KEY + REPLICA_2 + station,CACHE_STATION_TTL);
        }
    }
}
