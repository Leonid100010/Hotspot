package com.nju.edu.cn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static JSONObject strToJson(String text){
        return JSON.parseObject(text);
    }
}
