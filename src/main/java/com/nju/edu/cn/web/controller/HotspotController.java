package com.nju.edu.cn.web.controller;

import com.nju.edu.cn.web.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotspotController {

    /**
     * Hotspot主接口 TODO
     * @param station 目标站点
     * @return
     */
    @GetMapping("/getHotSpot")
    public Response getHotSpot(@RequestParam("station") String station){
        return new Response("200", "success", "result");
    }
}
