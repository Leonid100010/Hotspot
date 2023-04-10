package com.nju.edu.cn.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.SentiStrength;
import com.nju.edu.cn.service.SentiStrengthService;
import com.nju.edu.cn.util.JsonUtil;
import com.nju.edu.cn.util.okHttp.OkHttp;
import org.springframework.stereotype.Service;

@Service
public class SentiStrengthServiceImpl implements SentiStrengthService {

    @Override
    public SentiStrength calculateTrinary(String text) {

        String sentiRes = OkHttp.post("http://123.60.87.104:8085/analyse/trinaryAnalyse", "{\"text\": \" " + text + "\"}");

        JSONObject sentiObject = JsonUtil.strToJson(sentiRes);

        SentiStrength sentiStrength = JSON.parseObject(sentiObject.toJSONString(), SentiStrength.class);
        //转化为Java对象
        return sentiStrength;
    }
}
