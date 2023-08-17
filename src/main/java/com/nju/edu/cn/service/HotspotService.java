package com.nju.edu.cn.service;

import com.nju.edu.cn.entity.HotSpot;

public interface HotspotService {

    /**
     * 根据站点查询热点数据
     * @param station 站点
     * @return
     */
    HotSpot getHotSpotByStation(String station);

    HotSpot buildHotSpot(String station);

    void hotSpotRefresh(String station);
}
