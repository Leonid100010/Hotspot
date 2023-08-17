package com.nju.edu.cn.common.enums.base;

/**
 * @author: hwang
 * @date: 2023/8/17 23:35
 * @description:
 */
public interface ErrorCodeEnum {
    /**
     * default error message
     * @return
     */
    String getMessage();

    /**
     * short error code
     * @return
     */
    String getCode();

    /**
     * enumeration name
     * @return
     */
    String getName();
}
