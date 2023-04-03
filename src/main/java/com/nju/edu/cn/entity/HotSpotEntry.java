package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotSpotEntry {

    private String title;

    private String describe;

    private String hot;

    private int index;

    private SentiStrength sentiStrength;

}
