package com.cm.weblog.web;

import com.cm.weblog.common.domain.dos.UserDO;
import com.cm.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j // 自动生成日志实例
class WeblogWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testLog() {
        log.info("这是一行 INFO 级别日志");
        log.warn("这是一行 WARN 级别日志");
        log.error("这是一行 ERROR 级别日志");

        // 占位符
        String author = "CM";
        log.info("这是一行带有占位符的日志记录，作者：{}", author);
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    void insertTest() {
        // 构建数据库实体类
        UserDO userDO = UserDO.builder()
                .username("admin")
                .password("123456")
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build();

        userMapper.insert(userDO);
    }
}
