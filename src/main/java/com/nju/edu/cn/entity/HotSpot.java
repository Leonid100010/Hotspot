package com.nju.edu.cn.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotSpot {

    /**
     * 网站
     */
    private String station;

    /**
     * 网站描述
     */
    private String desc;

    /**
     * 热门更新时间
     */
    private String updateTime;

    /**
     * 热搜列表
     */
    private List<HotSpotEntry> hotSpotEntryList;

    private Double avgPos;

    private Double avgNeg;

    private Double avgTrinary;

    public boolean addHotSpotEntry(HotSpotEntry entry){
        if(hotSpotEntryList != null){
            this.hotSpotEntryList.add(entry);
            return true;
        }else{
            return  false;
        }

    }

    /**
     * 求 平均积极情绪
     */
    public void setAvgPos() {

        this.avgPos = calSentiAvgParam(1);
    }

    /**
     * 求 平均消极情绪
     */
    public void setAvgNeg(){

        this.avgNeg = calSentiAvgParam(-1);
    }

    /**
     * 求 平均中性情绪
     */
    public void setAvgTrinary(){

        this.avgTrinary = calSentiAvgParam(0);
    }


    public double calSentiAvgParam(int flag){
        double averageInit;
        if(flag == 1) averageInit = 1.0;
        else if(flag == -1) averageInit = -1.0;
        else averageInit = 0.0;

        int invalidCount = 0;
        if(!hotSpotEntryList.isEmpty()){
            double sum = 0.0;
            for(HotSpotEntry hotSpotEntry: hotSpotEntryList){
                if(hotSpotEntry.getSentiStrength() == null) {
                    invalidCount ++;
                    continue;}

                if(flag == 1) sum += hotSpotEntry.getSentiStrengthPos();
                else if(flag == -1) sum += hotSpotEntry.getSentiStrengthNeg();
                else sum += hotSpotEntry.getSentiStrengthTrinary();
            }
            if(hotSpotEntryList.size() - invalidCount > 0)
                averageInit = sum / hotSpotEntryList.size() - invalidCount;
        }
        return averageInit;
    }


    /**
     * 利用VO设置 entry中除了情绪分析之外的信息
     * @param hotSpotEntryVOS
     */
    public void setHotSpotEntryList(List<HotSpotEntryVO> hotSpotEntryVOS){
        if(this.hotSpotEntryList == null){
            this.hotSpotEntryList  = new ArrayList<>();
        }
        for(HotSpotEntryVO entryVO: hotSpotEntryVOS){
            HotSpotEntry entry = new HotSpotEntry();
            entry.setByHotSpotEntryVO(entryVO);
            this.hotSpotEntryList.add(entry);
        }
    }





}
