package com.nju.edu.cn.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotSpotEntryVO {

    /**
     * 站内热度排名
     */
    private int index;

    /**
     * 热门标题
     */
    private String title;

    /**
     * 热门描述
     */
    private String desc;

    /**
     * pic
     */
    private String pic;

    /**
     * 热度
     */
    private String hot;


    /**
     * url
     */
    private String url;

    /**
     * mobilUrl
     */
    private String mobilUrl;
}
