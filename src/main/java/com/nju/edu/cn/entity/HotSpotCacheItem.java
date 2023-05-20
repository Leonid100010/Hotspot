package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotSpotCacheItem {

    private String station;

    private String desc;

    private String updateTime;

    private Double avgPos;

    private Double avgNeg;

    private Double avgTrinary;

    /**
     * 热门条目集合在缓存中的id
     */
    private String hotSpotEntryListId;



}
