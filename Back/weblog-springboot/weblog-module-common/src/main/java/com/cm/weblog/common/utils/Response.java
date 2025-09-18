package com.cm.weblog.common.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义响应工具类
 */
@Data
public class Response<T> implements Serializable {
    private boolean success = true;
    private String errorMessage;
    private String errorCode;
    private T data;

    /*
    * 成功响应
    * */
    public static <T> Response<T> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    /*
    * 失败响应
    * */
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMessage(message);
        return response;
    }

    public static <T> Response<T> fail(String code, String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(code);
        response.setErrorMessage(message);
        return response;
    }
}
