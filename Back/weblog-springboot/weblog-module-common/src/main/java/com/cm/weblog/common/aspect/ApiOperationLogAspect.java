package com.cm.weblog.common.aspect;

import com.cm.weblog.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 自定义切面类
 */
@Aspect // 声明该类为一个切面类
@Component
@Slf4j
public class ApiOperationLogAspect {
    /**
     * 以自定义 @ApiOperationLog 注解为切点
     * 凡是添加该注解的方法都会执行环绕中的代码
     */
    @Pointcut("@annotation(com.cm.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog() {}

    /**
     * 环绕
     * @param joinPoint 切点
     * @return 请求结果
     * @throws Throwable 异常
     */
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();

            MDC.put("traceId", UUID.randomUUID().toString());

            // 获取处理请求的类和方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            // 获取请求入参并转成 JSON 字符串
            Object[] args = joinPoint.getArgs();
            String argsJson = Arrays.stream(args).map(toJsonString()).collect(Collectors.joining(", "));

            // 功能描述信息
            String description = getApiOperationLogDescription(joinPoint);

            // 打印入参
            log.info("====== 请求开始：[{}]，入参：{}，请求类：{}, 请求方法：{} ============================ ", description, argsJson, className, methodName);

            // 执行切点方法
            Object result = joinPoint.proceed(args);

            // 计算执行耗时
            long executionTime = System.currentTimeMillis() - startTime;

            // 打印出参
            log.info("====== 请求结束：[{}]，耗时：{}ms， 出参：{} ============================ ", description, executionTime, JsonUtil.toJson(result));

            return result;
        } finally {
            MDC.clear();
        }
    }

    /**
     * 转 JSON
     * @return Function
     */
    private Function<Object, String> toJsonString() {
        return JsonUtil::toJson;
    }

    /**
     * 获取注解的描述信息
     * @param joinPoint 切点
     * @return 注解描述
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取被注解的方法
        Method method = methodSignature.getMethod();

        // 提取注解
        ApiOperationLog annotation = method.getAnnotation(ApiOperationLog.class);

        // 提取 description 属性
        return annotation.description();
    }
}
