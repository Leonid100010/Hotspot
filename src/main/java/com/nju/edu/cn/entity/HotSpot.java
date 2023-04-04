package com.nju.edu.cn.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
