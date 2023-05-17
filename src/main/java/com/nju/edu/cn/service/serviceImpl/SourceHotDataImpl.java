package com.nju.edu.cn.service.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.HotSpotEntryVO;
import com.nju.edu.cn.entity.HotSpotVO;
import com.nju.edu.cn.service.SourceHotData;
import com.nju.edu.cn.util.okHttp.OkHttp;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SourceHotDataImpl implements SourceHotData {


    /**
     * 获取原始热点数据
     * @param station 站点
     * @return
     */
    @Override
    public String getHotDataByStation(String station) {

        HashMap<String , String > stationUrls = new HashMap<>();
        stationUrls.put("baidu", "https://api.vvhan.com/api/hotlist?type=baiduRD");
        stationUrls.put("bilibili", "https://api.vvhan.com/api/hotlist?type=bili");
        stationUrls.put("weibo", "https://api.vvhan.com/api/hotlist?type=wbHot");
        stationUrls.put("zhihu", "https://api.vvhan.com/api/hotlist?type=zhihuHot");

        if(stationUrls.containsKey(station)){
            return OkHttp.get(stationUrls.get(station));
        }
        return null;
    }

    @Override
    public HotSpotVO generateHotSpotVO(JSONObject jsonObject) {
        //转化成 实体类
        HotSpotVO hotSpot = new HotSpotVO();
        hotSpot.setStation(jsonObject.getString("title"));
        hotSpot.setDesc(jsonObject.getString("subtitle"));
        hotSpot.setUpdateTime(jsonObject.getString("update_time"));

        JSONArray hotEntries = (JSONArray) jsonObject.get("data");
        List<HotSpotEntryVO> hotSpotEntryVOs = JSONArray.parseArray(hotEntries.toString(), HotSpotEntryVO.class);

        //数据过多，截取前30个
        if(hotSpotEntryVOs.size() > 30){
            hotSpot.setHotSpotEntryVOList(hotSpotEntryVOs.subList(0,30));
        }else{
            hotSpot.setHotSpotEntryVOList(hotSpotEntryVOs);
        }

        return hotSpot;
    }
}
