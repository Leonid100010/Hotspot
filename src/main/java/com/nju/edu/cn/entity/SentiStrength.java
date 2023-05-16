package com.nju.edu.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentiStrength implements Serializable {

    private Integer pos;

    private Integer neg;

    private Integer trinary;
}
