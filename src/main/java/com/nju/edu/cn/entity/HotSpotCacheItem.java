package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotSpotCacheItem {
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

    private Double avgPos;

    private Double avgNeg;

    private Double avgTrinary;

    /**
     * 热门条目集合在缓存中的id
     */
    private String hotSpotEntryListId;



}
