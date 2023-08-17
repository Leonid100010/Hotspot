package com.nju.edu.cn.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String code;
    private String msg;
    private Object result;

    private Boolean success;

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Response buildSuccess(Object result) {
        return new Response("00000", "Success", result, Boolean.TRUE);
    }

    public static Response buildFailed(String code, RuntimeException e) {
        return new Response(code, e.getLocalizedMessage(), null, Boolean.FALSE);
    }

    public static Response buildFailed(String code, String msg) {
        return new Response(code, msg, null, Boolean.FALSE);
    }
}
