package com.nju.edu.cn.service.cache;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.nju.edu.cn.common.enums.RunErrorCodeEnum;
import com.nju.edu.cn.common.exception.BaseException;
import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.entity.HotSpotCacheItem;
import com.nju.edu.cn.entity.HotSpotEntry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
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

    @Resource
    RedisService redisService;

    /**
     * 缓存中是否存在该站点的热门信息
     * @param station 站点名称
     * @return
     */
    public HotSpot exist(String station){

        if(!redisService.hasKey(STATION_KEY + station)){
            // 缓存中不存在该站点
            // TODO: 缓存穿透问题
            return null;
        }
        // 无副本缓存
        return existOne(station, "");
    }

    public HotSpot existOne(String station, String replica){
        HotSpotCacheItem hotSpotCacheItem = getHotSpotCacheItem(STATION_KEY + replica + station);

        if(!redisService.hasKey(hotSpotCacheItem.getHotSpotEntryListId())) {
            throw new BaseException(RunErrorCodeEnum.CACHE_NO_STATION_LIST,
                    MessageFormat.format("the hotspot entry list of station {0} is not in cache", station));
        }
        // 获取HotSpotEntryList
        String hotSpotEntryListJson = redisService.getString(hotSpotCacheItem.getHotSpotEntryListId());

        List<HotSpotEntry> hotSpotEntryList = JSONUtil.toList(hotSpotEntryListJson, HotSpotEntry.class);
        // 构建HotSpot对象
        HotSpot hotSpot = BeanUtil.copyProperties(hotSpotCacheItem, HotSpot.class);
        hotSpot.setHotSpotEntryListAll(hotSpotEntryList);

        return hotSpot;
    }

    /**
     * 将该站点的信息加入缓存
     * @param hotSpot
     */
    public void setHotSpotCache(HotSpot hotSpot, String station){
        // 无副本版本
        setOneHotSpot(hotSpot, station, "");
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

        // 存储HotSpot基本信息, 缓存不过期
        Map<String, Object> stationMap = BeanUtil.beanToMap(hotSpotCacheItem, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        redisService.putMap(STATION_KEY +replica + station, stationMap);

//        // 过期时间: 30min
//        redisService.expire(STATION_KEY + replica + station, CACHE_STATION_TTL);

        // 存储hotSpotEntryList
        redisService.putString(STATION_ENTRY_LIST_KEY + replica + station, JSONUtil.toJsonStr(hotSpot.getHotSpotEntryList()));

    }

    public HotSpotCacheItem getHotSpotCacheItem(String key){
        Map<Object, Object> stationMap = redisService.getMap(key);

        return BeanUtil.fillBeanWithMap(stationMap, new HotSpotCacheItem(),false);

    }



}
