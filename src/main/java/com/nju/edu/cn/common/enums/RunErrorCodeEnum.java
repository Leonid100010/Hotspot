package com.nju.edu.cn.common.enums;

import com.nju.edu.cn.common.enums.base.ErrorCodeEnum;

/**
 * @author: hwang
 * @date: 2023/8/17 23:38
 * @description: 错误码
 */
public enum RunErrorCodeEnum implements ErrorCodeEnum {
    /**
     * 通用错误码
     */
    COMMON_PARAM_ERROR("A0000", "param not valid"),
    COMMON_SYSTEM_ERROR("A0001", "common system error"),

    /**
     * cache异常
     */
    CACHE_NO_STATION_LIST("A1000", "the entry list of station cache does not exist"),

    ;

    private String code;

    private String message;

    RunErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name();
    }
}
