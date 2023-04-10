package com.nju.edu.cn.service;

import com.nju.edu.cn.entity.SentiStrength;

public interface SentiStrengthService {

    SentiStrength calculateTrinary(String text);
}
