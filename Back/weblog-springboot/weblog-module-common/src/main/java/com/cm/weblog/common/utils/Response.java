package com.cm.weblog.common.utils;

import com.cm.weblog.common.exception.BaseExceptionInterface;
import com.cm.weblog.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应参数工具类
 */
@Data
public class Response<T> implements Serializable {
    private boolean success = true;
    private String message;
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
        response.setSuccess(true);
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
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> fail(String code, String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(code);
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> fail(BizException bizException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(bizException.getErrorMessage());
        response.setErrorCode(bizException.getErrorCode());
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(baseExceptionInterface.getErrorMessage());
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        return response;
    }
}
