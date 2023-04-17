package com.nju.edu.cn.service;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.HotSpotEntryVO;
import com.nju.edu.cn.entity.HotSpotVO;
import com.nju.edu.cn.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SourceHotDataTest {

    SourceHotData sourceHotData;

    @Autowired
    public SourceHotDataTest(SourceHotData sourceHotData) {
        this.sourceHotData = sourceHotData;
    }


    @Test
    void testGenerateHotSpotVO(){
        JSONObject jsonObject = JsonUtil.strToJson(sourceHotData.getHotDataByStation("baidu"));
        HotSpotVO hotSpotVO = sourceHotData.generateHotSpotVO(jsonObject);

        for (HotSpotEntryVO vo: hotSpotVO.getHotSpotEntryVOList()) {
            System.out.println(vo);
        }
    }

    @Test
    void testGetHotDataByStation(){
        System.out.println(
                sourceHotData.getHotDataByStation("baidu")
        );
    }
}
