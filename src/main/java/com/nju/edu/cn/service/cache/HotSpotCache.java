package com.nju.edu.cn.service.cache;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.entity.HotSpotCacheItem;
import com.nju.edu.cn.entity.HotSpotEntry;
import com.nju.edu.cn.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nju.edu.cn.service.cache.RedisConstants.*;

/**
 * HotSpot的缓存操作类
 * HotSpot(除了hotSpotEntryList）, 即HotSpotCacheItem使用redis的哈希结构存储
 * HotSpot中的hotSpotEntryList使用redis的字符串结构存储, key对应HotSpotCacheItem中的hotSpotEntryListId
 */
@Service
public class HotSpotCache {

    RedisService redisService;

    @Autowired
    public HotSpotCache(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 缓存中是否存在该站点的热门信息
     * @param station 站点名称
     * @return
     */
    public HotSpot exist(String station){

        if(!redisService.hasKey(STATION_KEY + REPLICA_1 + station)
                && !redisService.hasKey(STATION_KEY + REPLICA_2 + station)){
            // 缓存中不存在该站点
            // TODO: 缓存穿透问题
            return null;
        }
        if(!redisService.hasKey(STATION_KEY + REPLICA_1 + station)){
            // return replica2
            return existOne(station,REPLICA_2);
        }
        if(!redisService.hasKey(STATION_KEY + REPLICA_2 + station)){
            // return replica1
            return existOne(station, REPLICA_1);
        }
        // 选择update_time 更近的返回
        Map<Object, Object> map1 = redisService.getMap(STATION_KEY + REPLICA_1 + station);
        Map<Object, Object> map2 = redisService.getMap(STATION_KEY + REPLICA_2 + station);
        if(map1 != null && map2 != null){
            if(TimeUtil.compare((String) map1.get("update_time"), (String) map2.get("update_time")) == 1){
                //replica2更近
                return existOne(station, REPLICA_2);
            }else {
                return existOne(station, REPLICA_1);
            }
        }else{
            // TODO: 下面的判断仅为了防止出现：
            // 在54行：getMap的过程中某个副本过期了，大概率是副本2过期，那么会导致的空指针问题。
            if(map1 == null){
                if(map2 == null){
                    return null;
                }else{
                    return existOne(station, REPLICA_2);
                }
            }else{
                return existOne(station, REPLICA_1);
            }
        }

    }

    public HotSpot existOne(String station, String replica){
        HotSpotCacheItem hotSpotCacheItem = getHotSpotCacheItem(STATION_KEY + replica + station);

        if(!redisService.hasKey(hotSpotCacheItem.getHotSpotEntryListId())) {
            return null;
        }
        //获取HotSpotEntryList
        String hotSpotEntryListJson = redisService.getString(hotSpotCacheItem.getHotSpotEntryListId());

        List<HotSpotEntry> hotSpotEntryList = JSONUtil.toList(hotSpotEntryListJson, HotSpotEntry.class);
        //构建HotSpot对象
        HotSpot hotSpot = BeanUtil.copyProperties(hotSpotCacheItem, HotSpot.class);
        hotSpot.setHotSpotEntryListAll(hotSpotEntryList);

        return hotSpot;
    }

    /**
     * 将该站点的信息加入缓存
     * @param hotSpot
     */
    public void setHotSpotCache(HotSpot hotSpot, String station){
        if(!redisService.hasKey(STATION_KEY + REPLICA_1 + station) ||
                !redisService.hasKey(STATION_ENTRY_LIST_KEY + REPLICA_1 + station)){
            //副本1不存在
            setOneHotSpot(hotSpot, station, REPLICA_1);
        }else{
            setOneHotSpot(hotSpot, station, REPLICA_2);
        }
    }

    public void setOneHotSpot(HotSpot hotSpot, String station, String replica){
        HotSpotCacheItem hotSpotCacheItem = new HotSpotCacheItem(
                hotSpot.getStation(),
                hotSpot.getDesc(),
                hotSpot.getUpdateTime(),
                hotSpot.getAvgPos(),
                hotSpot.getAvgNeg(),
                hotSpot.getAvgTrinary(),
                STATION_ENTRY_LIST_KEY + replica + station
        );

        //存储HotSpot基本信息
        Map<String, Object> stationMap = BeanUtil.beanToMap(hotSpotCacheItem, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        redisService.putMap(STATION_KEY +replica + station, stationMap);
        //过期时间: 30min
        redisService.expire(STATION_KEY + replica + station, CACHE_STATION_TTL);

        //存储hotSpotEntryList
        redisService.putString(STATION_ENTRY_LIST_KEY + replica + station, JSONUtil.toJsonStr(hotSpot.getHotSpotEntryList()),
                CACHE_STATION_TTL );

    }


    public HotSpotCacheItem getHotSpotCacheItem(String key){
        Map<Object, Object> stationMap = redisService.getMap(key);

        return BeanUtil.fillBeanWithMap(stationMap, new HotSpotCacheItem(),false);

    }



}
