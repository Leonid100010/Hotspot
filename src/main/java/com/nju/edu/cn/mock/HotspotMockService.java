package com.nju.edu.cn.mock;


import com.nju.edu.cn.entity.HotSpotEntry;
import com.nju.edu.cn.entity.SentiStrength;
import org.springframework.stereotype.Service;
import com.nju.edu.cn.entity.HotSpot;

import java.util.ArrayList;
import java.util.List;

/**
 * HotSpot桩服务
 */
@Service
public class HotspotMockService {

    /**
     * 生成硬编码数据
     * @param station 站点
     * @return
     */
    public HotSpot generateHotSpot(String station){
        SentiStrength sentiStrength = new SentiStrength(1, -1, 1);

        HotSpotEntry hotSpotEntry1 = new HotSpotEntry(
                "在泰杀害女留学生3嫌犯湖北襄阳落网",
                "4月4日，在泰国绑架勒索22岁大三女留学生金某50万人民币未果，将其杀害抛尸水池的3名嫌疑人，逃窜回中国后，已被中国警方在湖北襄阳逮捕。",
                "498.2万",
                1,
                "https://www.baidu.com/s?ie=UTF-8&wd=%E5%9C%A8%E6%B3%B0%E6%9D%80%E5%AE%B3%E5%A5%B3%E7%95%99%E5%AD%A6%E7%94%9F3%E5%AB%8C%E7%8A%AF%E6%B9%96%E5%8C%97%E8%A5%84%E9%98%B3%E8%90%BD%E7%BD%91",
                sentiStrength
        );

        HotSpotEntry hotSpotEntry2 = new HotSpotEntry("华科大有宿舍遭遇雷击？校方辟谣",
                "4月4日，有网友发帖称，华中科技大学有宿舍遭遇雷击。记者从校方了解到，确实有宿舍吊顶被风吹落，但事情与雷电无关，目前吊顶已经修好。",
                "484.1万",
                2,
                "https://www.baidu.com/s?ie=UTF-8&wd=%E5%8D%8E%E7%A7%91%E5%A4%A7%E6%9C%89%E5%AE%BF%E8%88%8D%E9%81%AD%E9%81%87%E9%9B%B7%E5%87%BB%EF%BC%9F%E6%A0%A1%E6%96%B9%E8%BE%9F%E8%B0%A3",
                sentiStrength);

        List<HotSpotEntry> hotSpotEntryList = new ArrayList<>();

        HotSpot hotSpot = new HotSpot(
                station,
                "百度热点",
                "2023-04-04 05:56:02",
                hotSpotEntryList,
                1.0,
                -1.0,
                1.0
        );
        hotSpot.addHotSpotEntry(hotSpotEntry1);
        hotSpot.addHotSpotEntry(hotSpotEntry2);

        return hotSpot;
    }

}
