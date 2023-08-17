package com.nju.edu.cn.web.controller;

import com.nju.edu.cn.common.enums.RunErrorCodeEnum;
import com.nju.edu.cn.common.exception.BaseException;
import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.service.HotspotService;
import com.nju.edu.cn.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
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
        try{
            HotSpot hotSpot = hotspotService.getHotSpotByStation(station);
            if(hotSpot == null){
                return Response.buildFailed("11111","查询失败");
            }
            return Response.buildSuccess(hotSpot);
        }catch (BaseException e){
            log.error(e.getDetailReason(), e);
            return Response.buildFailed(e.getRunErrorCodeEnum().getCode(), e.getDetailReason());
        }catch (Exception e){
            String errorMsg = String.format("hotspot query error, station: %s", station);
            log.error(errorMsg, e);
            return Response.buildFailed(RunErrorCodeEnum.COMMON_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }
}
