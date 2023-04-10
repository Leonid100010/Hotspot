package com.nju.edu.cn.service.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.entity.HotSpotEntry;
import com.nju.edu.cn.entity.HotSpotEntryVO;
import com.nju.edu.cn.entity.HotSpotVO;
import com.nju.edu.cn.service.SourceHotData;
import com.nju.edu.cn.util.okHttp.OkHttp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceHotDataImpl implements SourceHotData {

    /**
     * 获取原始热点数据
     * //TODO：不同的站点在这里区分
     * @param station 站点
     * @return
     */
    @Override
    public String getHotDataByStation(String station) {

        String sourceData = OkHttp.get("https://api.vvhan.com/api/hotlist?type=baiduRD");


        return sourceData;
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
        hotSpot.setHotSpotEntryVOList(hotSpotEntryVOs);



        return hotSpot;
    }
}
