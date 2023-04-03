package com.nju.edu.cn.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotSpot {

    private String station;

    private String desc;

    private String updateTime;

    private List<HotSpotEntry> hotSpotEntryList;
}
