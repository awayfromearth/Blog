package com.cm.weblog.web.controller;

import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.exception.BizException;
import com.cm.weblog.common.utils.Response;
import com.cm.weblog.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@Slf4j
public class TestController {
    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response<?> test(@RequestBody @Validated User user, BindingResult bindingResult) {
        // 主动定义一个运行时异常，分母不能为零
        int i = 1 / 0;
        return Response.success();
    }
}
