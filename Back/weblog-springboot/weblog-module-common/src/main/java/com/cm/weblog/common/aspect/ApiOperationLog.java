package com.cm.weblog.common.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // 指定注解在运行时保留(可以通过反射在运行时被访问和解析)
@Target({ElementType.METHOD}) // 指定该注解作用于方法
@Documented // 生成文档时注解元素及注解信息会被包含
public @interface ApiOperationLog {
    /**
     * API 功能描述
     * @return String
     */
    String description() default "";
}
