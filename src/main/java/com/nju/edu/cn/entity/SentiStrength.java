package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentiStrength {

    private Integer pos;

    private Integer neg;

    private Integer trinary;
}
