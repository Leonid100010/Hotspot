package com.nju.edu.cn.service.schedule;

import com.nju.edu.cn.service.HotspotService;
import com.nju.edu.cn.service.SourceHotData;
import com.nju.edu.cn.service.cache.RedisService;
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

    RedisService redisService;

    HotspotService hotspotService;

    SourceHotData sourceHotData;

    /**
     * redis中map结构中的键
     */
    private static final String update_time = "updateTime";

    @Autowired
    public CacheRefresh(RedisService redisService, SourceHotData sourceHotData,
                        HotspotService hotspotService) {
        this.redisService = redisService;
        this.sourceHotData = sourceHotData;
        this.hotspotService = hotspotService;
    }

    public void refreshStationCache(String station){
        String stationKey = STATION_KEY + station;
        String stationEntryListKey = STATION_ENTRY_LIST_KEY + station;

        boolean hasCache =Boolean.TRUE.equals( redisService.hasKey(stationKey))
                    && Boolean.TRUE.equals(redisService.hasKey(stationEntryListKey));

        if( !hasCache ){
            // 缓存不存在
            refresh(station);
        }else if(checkUpdateTime(station) == 1){
            // 数据已经旧了
            refresh(station);
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
        // 获取源数据
        String currHotSpotData = sourceHotData.getHotDataByStation(station);
        String currTime = JsonUtil.strToJson(currHotSpotData).getString("update_time");

        Map<Object, Object> map = redisService.getMap(STATION_KEY + station);
        String cacheTime  = (String) map.get(update_time);

        log.info("cacheTime:" + cacheTime);
        log.info("currTime: " + currTime);
        return TimeUtil.compare(cacheTime, currTime) == 1? 1: 0;
    }
}
