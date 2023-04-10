package com.nju.edu.cn.service;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.HotSpotVO;

public interface SourceHotData {

    String getHotDataByStation(String station);

    HotSpotVO generateHotSpotVO(JSONObject jsonObject);
}
