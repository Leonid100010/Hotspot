package com.nju.edu.cn.cache;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.entity.HotSpotCacheItem;
import com.nju.edu.cn.entity.HotSpotEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.nju.edu.cn.cache.RedisConstants.*;

/**
 * HotSpot的缓存操作类
 * HotSpot(除了hotSpotEntryList）, 即HotSpotCacheItem使用redis的哈希结构存储
 * HotSpot中的hotSpotEntryList使用redis的字符串结构存储, key对应HotSpotCacheItem中的hotSpotEntryListId
 */
@Service
public class HotSpotCache {


    StringRedisTemplate stringRedisTemplate;

    @Autowired
    public HotSpotCache(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 缓存中是否存在该站点的热门信息
     * @param station
     * @return
     */
    public HotSpot exist(String station){

        if(Boolean.FALSE.equals(stringRedisTemplate.hasKey(STATION_KEY + station))){
            //缓存中不存在该站点
            //TODO: 缓存穿透问题
            return null;
        }

        //目标站点有缓存
        Map<Object, Object> stationMap = stringRedisTemplate.opsForHash().entries(STATION_KEY + station);

        HotSpotCacheItem hotSpotCacheItem = BeanUtil.fillBeanWithMap(stationMap,new HotSpotCacheItem(),false);

        if(Boolean.FALSE.equals(stringRedisTemplate.hasKey(hotSpotCacheItem.getHotSpotEntryListId()))){
            return null;
        }
        //获取HotSpotEntryList
        String hotSpotEntryListJson = stringRedisTemplate.opsForValue().get(hotSpotCacheItem.getHotSpotEntryListId());


        List<HotSpotEntry> hotSpotEntryList = JSONUtil.toList(hotSpotEntryListJson, HotSpotEntry.class);
        //构建HotSpot对象
        HotSpot hotSpot = BeanUtil.copyProperties(hotSpotCacheItem, HotSpot.class);
        hotSpot.setHotSpotEntryListAll(hotSpotEntryList);

        //热门数据应该定时刷新，不根据访问延长过期时间

        return hotSpot;

    }

    /**
     * 将该站点的信息加入缓存
     * @param hotSpot
     */
    public void setHotSpotCache(HotSpot hotSpot, String station){
        HotSpotCacheItem hotSpotCacheItem = new HotSpotCacheItem(
                hotSpot.getStation(),
                hotSpot.getDesc(),
                hotSpot.getUpdateTime(),
                hotSpot.getAvgPos(),
                hotSpot.getAvgNeg(),
                hotSpot.getAvgTrinary(),
                STATION_ENTRY_LIST_KEY + station
        );

        //存储HotSpot基本信息
        Map<String, Object> stationMap = BeanUtil.beanToMap(hotSpotCacheItem, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));


        stringRedisTemplate.opsForHash().putAll(STATION_KEY + station, stationMap);
        //过期时间: 10min
        //TODO：源数据变化触发缓存刷新
        stringRedisTemplate.expire(STATION_KEY + station, CACHE_STATION_TTL, TimeUnit.MINUTES);

        //存储hotSpotEntryList
        stringRedisTemplate.opsForValue().set(STATION_ENTRY_LIST_KEY +station,JSONUtil.toJsonStr(hotSpot.getHotSpotEntryList()),
                CACHE_STATION_TTL, TimeUnit.MINUTES);


    }
}
