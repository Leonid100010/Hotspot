package com.nju.edu.cn.service;

import com.nju.edu.cn.entity.HotSpot;

public interface HotspotService {

    HotSpot getHotSpotByStation(String station);

    HotSpot buildHotSpot(String station);

    void hotSpotRefresh(String station);
}
