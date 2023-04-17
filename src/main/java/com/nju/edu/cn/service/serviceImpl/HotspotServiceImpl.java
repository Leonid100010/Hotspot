package com.nju.edu.cn.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.cn.entity.HotSpot;
import com.nju.edu.cn.entity.HotSpotEntry;
import com.nju.edu.cn.entity.HotSpotVO;
import com.nju.edu.cn.entity.SentiStrength;
import com.nju.edu.cn.service.HotspotService;
import com.nju.edu.cn.service.SentiStrengthService;
import com.nju.edu.cn.service.SourceHotData;
import com.nju.edu.cn.service.TranslateService;
import com.nju.edu.cn.util.HotSpotUtil;
import com.nju.edu.cn.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HotspotServiceImpl implements HotspotService {

    SourceHotData sourceHotData;

    TranslateService translateService;

    SentiStrengthService sentiStrengthService;

    @Autowired
    public HotspotServiceImpl(SourceHotData sourceHotData, TranslateService translateService, SentiStrengthService sentiStrengthService) {
        this.sourceHotData = sourceHotData;
        this.translateService = translateService;
        this.sentiStrengthService = sentiStrengthService;
    }

    @Override
    public HotSpot getHotSpotByStation(String station) {
        //获取源数据
        JSONObject sourceDataObject = JsonUtil.strToJson(sourceHotData.getHotDataByStation(station));

        HotSpotVO hotSpotVO = sourceHotData.generateHotSpotVO(sourceDataObject);

        HotSpot hotSpot  = new HotSpot();
        hotSpot.setStation(hotSpotVO.getStation());
        hotSpot.setDesc(hotSpotVO.getDesc());
        hotSpot.setUpdateTime(hotSpotVO.getUpdateTime());
        hotSpot.setHotSpotEntryList(hotSpotVO.getHotSpotEntryVOList());

        //进行情绪分析
        for(HotSpotEntry entry:  hotSpot.getHotSpotEntryList()){
            //翻译
            //TODO: 这里还没解决拼接字符串的格式问题，仅使用标题进行计算
            if(entry.getTitle() == null || entry.getTitle().isEmpty()) {
                //TODO：空值问题
                entry.setSentiStrength(new SentiStrength(1,-1,0));
                continue;
            }
            String toCal = translateService.chineseToEng(entry.getTitle());
            if(toCal == null || toCal.isEmpty()) {
                entry.setSentiStrength(new SentiStrength(1,-1,0));
                continue;
            }
            //计算情绪指标
            entry.setSentiStrength(
                    sentiStrengthService.calculateTrinary(HotSpotUtil.format(toCal))
            );
        }

        hotSpot.setAvgNeg();
        hotSpot.setAvgPos();
        hotSpot.setAvgTrinary();

        return hotSpot;
    }
}
