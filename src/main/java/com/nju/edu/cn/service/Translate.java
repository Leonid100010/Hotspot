package com.nju.edu.cn.service;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.SingleText;
import com.nju.edu.cn.service.feign.TranslateService;
import com.nju.edu.cn.util.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Translate  {

    @Resource
    TranslateService translateService;

    /**
     * 利用openFeign调用翻译模块并提取json中的数据
     * @param text
     * @return
     */
    public String chineseToEng(String text) {
        String translateStr = translateService.translateSingle(new SingleText(text));


        JSONObject translateObj = JsonUtil.strToJson(translateStr);


        return translateObj.getString("target");
    }
}
