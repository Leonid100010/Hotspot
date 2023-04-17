package com.nju.edu.cn.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HotSpotServiceTest {

    HotspotService hotspotService;

    @Autowired
    public HotSpotServiceTest(HotspotService hotspotService) {
        this.hotspotService = hotspotService;
    }

    @Test
    void testGetHotSpotByStation(){
        System.out.println(hotspotService.getHotSpotByStation("baidu"));
    }
}
