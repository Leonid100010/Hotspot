package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotSpotEntry {

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
    private String describe;

    /**
     * 热度
     */
    private String hot;



    /**
     * url
     */
    private String url;

    /**
     * 情绪分析信息
     */
    private SentiStrength sentiStrength;


    public double getSentiStrengthPos(){ return sentiStrength.getPos() == null? 0.0: sentiStrength.getPos();}

    public double getSentiStrengthNeg() {
        System.out.println("title is:" + title);
        return  sentiStrength.getNeg() == null? 0.0: sentiStrength.getNeg();
    }

    public double getSentiStrengthTrinary() { return sentiStrength.getTrinary() == null? 0.0: sentiStrength.getTrinary(); }


    public void setByHotSpotEntryVO(HotSpotEntryVO hseVO){
        setIndex(hseVO.getIndex());
        setTitle(hseVO.getTitle());
        setDescribe(hseVO.getDesc());
        setHot(hseVO.getHot());
        setUrl(hseVO.getUrl());

    }

    public void setSentiStrength(SentiStrength senti){
        if(this.sentiStrength == null){
            this.sentiStrength = new SentiStrength(senti.getPos(), senti.getNeg(), senti.getTrinary());
        }else{
            this.sentiStrength.setPos(senti.getPos());
            this.sentiStrength.setNeg(senti.getNeg());
            this.sentiStrength.setTrinary(senti.getTrinary());
        }
    }


}