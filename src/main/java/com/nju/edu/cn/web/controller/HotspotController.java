package com.nju.edu.cn.web.controller;

import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.service.HotspotService;
import com.nju.edu.cn.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotspotController {

    HotspotService hotspotService;

    @Autowired
    public HotspotController(HotspotService hotspotService) {
        this.hotspotService = hotspotService;
    }

    /**
     * Hotspot主接口
     * @param station 目标站点
     * @return
     */
    @GetMapping("/getHotSpot")
    public Response getHotSpot(@RequestParam("station") String station){
        HotSpot hotSpot = hotspotService.getHotSpotByStation(station);
        if(hotSpot == null){
            return Response.buildFailed("11111","查询失败");
        }
        return Response.buildSuccess(hotSpot);
    }
}
