package com.nju.edu.cn.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.service.TranslateService;
import com.nju.edu.cn.util.JsonUtil;
import com.nju.edu.cn.util.okHttp.OkHttp;
import org.springframework.stereotype.Service;

@Service
public class TranslateServiceImpl implements TranslateService {

    @Override
    public String chineseToEng(String text) {
        String translateResStr = OkHttp.post("http://123.60.87.104:9100/translate/single",
                "{ \"text\": \"" + text + "\" }");

        JSONObject translateObj = JsonUtil.strToJson(translateResStr);


        return translateObj.getString("target");
    }
}
