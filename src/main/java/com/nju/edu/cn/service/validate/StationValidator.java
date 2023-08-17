package com.nju.edu.cn.service.validate;

import com.nju.edu.cn.common.enums.StationEnum;
import org.apache.commons.lang.StringUtils;

/**
 * @author: hwang
 * @date: 2023/8/17 23:20
 * @description: 校验站点是否支持查询
 */
public class StationValidator {

    public static boolean valid(String station){

        if(StringUtils.isBlank(station)){
            return false;
        }
        if(null == StationEnum.of(station)){
            return false;
        }
        return true;

    }


}
