package com.cm.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.cm.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}
