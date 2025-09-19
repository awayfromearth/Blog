package com.cm.weblog.common.utils;

import com.cm.weblog.common.exception.BaseExceptionInterface;
import com.cm.weblog.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义响应工具类
 */
@Data
public class Response<T> implements Serializable {
    private boolean success = true;
    private String message;
    private String code;
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
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> fail(String code, String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    /**
     * 处理业务异常
     * @param bizException 自定义业务异常
     * @return Response
     * @param <T> ?
     */
    public static <T> Response<T> fail(BizException bizException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setCode(bizException.getErrorCode());
        response.setMessage(bizException.getErrorMessage());
        return response;
    }

    /**
     * 支持直接传入异常码枚举
     * @param baseExceptionInterface 枚举
     * @return Response
     * @param <T> ?
     */
    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setCode(baseExceptionInterface.getErrorCode());
        response.setMessage(baseExceptionInterface.getErrorMessage());
        return response;
    }
}
