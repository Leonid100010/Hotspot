package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotSpotVO {

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
    private List<HotSpotEntryVO> hotSpotEntryVOList;



}
