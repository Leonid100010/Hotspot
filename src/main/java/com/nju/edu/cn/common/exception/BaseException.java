package com.nju.edu.cn.common.exception;

import com.nju.edu.cn.common.enums.RunErrorCodeEnum;

/**
 * @author: hwang
 * @date: 2023/8/17 23:33
 * @description: 自定义异常类
 */
public class BaseException extends RuntimeException{

    /**
     * 错误码信息
     */
    protected RunErrorCodeEnum runErrorCodeEnum;

    /**
     * 自定义信息
     */
    protected String detailReason;

    protected BaseException(){

    }

    public BaseException(RunErrorCodeEnum runErrorCodeEnum){
        this.runErrorCodeEnum = runErrorCodeEnum;
    }

    public BaseException(RunErrorCodeEnum runErrorCodeEnum, String detailReason){
        this.runErrorCodeEnum = runErrorCodeEnum;
        this.detailReason = detailReason;
    }

    public BaseException(RunErrorCodeEnum runErrorCodeEnum, String detailReason, Throwable t){
        super(t);
        this.runErrorCodeEnum = runErrorCodeEnum;
        this.detailReason = detailReason;
    }

    public RunErrorCodeEnum getRunErrorCodeEnum() {
        return runErrorCodeEnum;
    }

    public String getDetailReason() {
        return detailReason;
    }

    public void setRunErrorCodeEnum(RunErrorCodeEnum runErrorCodeEnum) {
        this.runErrorCodeEnum = runErrorCodeEnum;
    }

    public void setDetailReason(String detailReason) {
        this.detailReason = detailReason;
    }
}
