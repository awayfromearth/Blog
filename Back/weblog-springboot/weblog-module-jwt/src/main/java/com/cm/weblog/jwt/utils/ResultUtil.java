package com.cm.weblog.jwt.utils;

import com.cm.weblog.common.utils.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理返回参数
 */
public class ResultUtil {
    /**
     * 成功时响应参数
     * @param response 请求响应
     * @param result 请求结果
     * @throws IOException 异常
     */
    public static void ok(HttpServletResponse response, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败时响应参数
     * @param response 请求响应
     * @param result 请求结果
     * @throws IOException 异常
     */
    public static void fail(HttpServletResponse response, Response<?> result) throws IOException {
        ok(response, result);
    }

    /**
     * 失败时响应参数
     * @param response 请求响应
     * @param status 响应状态
     * @param result 请求结果
     * @throws IOException 异常
     */
    public static void fail(HttpServletResponse response, int status, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
