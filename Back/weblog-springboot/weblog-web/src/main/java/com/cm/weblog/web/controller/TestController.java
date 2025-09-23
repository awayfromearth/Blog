package com.cm.weblog.web.controller;

import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.exception.BizException;
import com.cm.weblog.common.utils.JsonUtil;
import com.cm.weblog.common.utils.Response;
import com.cm.weblog.web.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 * 测试请求类
 */
@RestController
@Slf4j
@Api(tags = "首页模块")
public class TestController {
    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    @ApiOperation(value = "测试接口")
    public Response<?> test(@RequestBody @Validated User user) {
        /*
            项目初始化启动测试代码
        */
        // return user;

        /*
            参数校验测试代码
        */
        /*if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining("，"));

            return ResponseEntity.badRequest().body(errorMessage);
        }

        return ResponseEntity.ok("参数没有任何问题");*/

        /*
            自定义响应工具类测试代码
        */
        /*if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining("，"));

            return Response.fail(errorMessage);
        }
        return Response.success(user);*/

        /*
            自定义业务异常测试代码
        */
        /*throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);*/

        /*
            运行时异常测试代码
        */
        /*int i = 1 / 0;
        return Response.success();*/

        /*
            全局处理参数校验异常测试代码：去除参数BindingResult
        */
        /*return Response.success();*/

        /*
        *   测试 Jackson 日期序列化及反序列化配置
        * */
        log.info(JsonUtil.toJson(user));

        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());

        return Response.success(user);
    }
}
