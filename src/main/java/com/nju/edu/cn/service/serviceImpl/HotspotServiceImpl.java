package com.nju.edu.cn.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.service.cache.HotSpotCache;
import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.entity.HotSpotEntry;
import com.nju.edu.cn.entity.HotSpotVO;
import com.nju.edu.cn.service.*;
import com.nju.edu.cn.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HotspotServiceImpl implements HotspotService {

    SourceHotData sourceHotData;

    Translate translate;

    SentiStrengthServiceEnter sentiStrengthService;

    HotSpotCache hotSpotCache;

    @Autowired
    public HotspotServiceImpl(SourceHotData sourceHotData, Translate translate,
                              SentiStrengthServiceEnter sentiStrengthService, HotSpotCache hotSpotCache) {
        this.sourceHotData = sourceHotData;
        this.sentiStrengthService = sentiStrengthService;
        this.translate = translate;
        this.hotSpotCache = hotSpotCache;
    }

    @Override
    public HotSpot getHotSpotByStation(String station) {
        //检查缓存
        HotSpot hotSpotCa = hotSpotCache.exist(station);
        if(hotSpotCa != null){
            log.info("站点 " + station + " 缓存命中!");
            return hotSpotCa;
        }
        log.info("站点 " + station + " 缓存未命中");

        HotSpot hotSpot = buildHotSpot(station);

        //加入缓存
        hotSpotCache.setHotSpotCache(hotSpot, station);

        return hotSpot;
    }

    /**
     * 核心业务逻辑：获取源数据 -》翻译 -》情绪分析 -》构建对象
     * @param station
     * @return
     */
    public HotSpot buildHotSpot(String station){
        //获取源数据
        log.info("获取热点源数据中......");
        String source = sourceHotData.getHotDataByStation(station);
        if(source == null){
            return null;
        }
        JSONObject sourceDataObject = JsonUtil.strToJson(source);

        HotSpotVO hotSpotVO = sourceHotData.generateHotSpotVO(sourceDataObject);

        log.info("站点 " + station + " 的热门条目共有 " + hotSpotVO.getHotSpotEntryVOList().size() + " 个");


        HotSpot hotSpot  = new HotSpot();
        hotSpot.setStation(hotSpotVO.getStation());
        hotSpot.setDesc(hotSpotVO.getDesc());
        hotSpot.setUpdateTime(hotSpotVO.getUpdateTime());
        hotSpot.setHotSpotEntryList(hotSpotVO.getHotSpotEntryVOList());

        log.info("情绪分析中......");
        //进行情绪分析
        for(HotSpotEntry entry:  hotSpot.getHotSpotEntryList()){
            //拼接字符串
            String toTrans;
            if(entry.getTitle() == null || entry.getTitle().isEmpty()) {
                //如果title为空
                toTrans = entry.getDescribe();
            }else{
                toTrans = entry.getTitle() + " " + entry.getDescribe();
            }
            //翻译
            String toCal = translate.chineseToEng(toTrans);

            //计算情绪指标
            entry.setSentiStrength(
                    sentiStrengthService.calculateTrinary(toCal)
            );
        }


        hotSpot.setAvgNeg();
        hotSpot.setAvgPos();
        hotSpot.setAvgTrinary();

        log.info("站点： " + station + " 分析结束");

        return hotSpot;
    }

    public void hotSpotRefresh(String station){

        hotSpotCache.setHotSpotCache(buildHotSpot(station),station);
    }
}
