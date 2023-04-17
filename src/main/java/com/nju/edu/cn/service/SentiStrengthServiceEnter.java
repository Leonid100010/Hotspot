package com.nju.edu.cn.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.SentiStrength;
import com.nju.edu.cn.entity.SingleText;
import com.nju.edu.cn.service.feign.SentiStrengthService;
import com.nju.edu.cn.util.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SentiStrengthServiceEnter {

    @Resource
    SentiStrengthService sentiStrengthService;

    /**
     * 调用sentistrength模块接口，将json结果转化为SentiStrength
     * @param text
     * @return
     */
    public SentiStrength calculateTrinary(String text) {

        String sentiRes = sentiStrengthService.trinaryAnalyse(new SingleText(text));

        JSONObject sentiObject = JsonUtil.strToJson(sentiRes);

        //转化为Java对象
        return JSON.parseObject(sentiObject.toJSONString(), SentiStrength.class);
    }
}
