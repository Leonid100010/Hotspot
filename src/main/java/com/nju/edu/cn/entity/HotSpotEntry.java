package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotSpotEntry {


    /**
     * 热门标题
     */
    private String title;

    /**
     * 热门描述
     */
    private String describe;

    /**
     * 热度
     */
    private String hot;

    /**
     * 站内热度排名
     */
    private int index;

    /**
     * url
     */
    private String url;

    /**
     * 情绪分析信息
     */
    private SentiStrength sentiStrength;

}
