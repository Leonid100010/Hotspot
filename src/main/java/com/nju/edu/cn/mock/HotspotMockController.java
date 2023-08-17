package com.nju.edu.cn.mock;

import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Hotspot 桩接口
 */
@RestController
@RequestMapping("/mock")
public class HotspotMockController {

    private final HotspotMockService hotspotMockService;

    @Autowired
    public HotspotMockController(HotspotMockService hotspotMockService) {
        this.hotspotMockService = hotspotMockService;
    }

    @GetMapping("/getHotSpot")
    public Response getHotSpot(@RequestParam("station") String station){
        HotSpot res = hotspotMockService.generateHotSpot(station);

        return Response.buildSuccess(res);
    }

}
