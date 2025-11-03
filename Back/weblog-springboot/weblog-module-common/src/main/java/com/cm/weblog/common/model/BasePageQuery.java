package com.cm.weblog.common.model;

import lombok.Data;

/**
 * 分页请求基础类
 */
@Data
public class BasePageQuery {
    // 当前页码，默认 1
    private Long current = 1L;

    // 每页数据量，默认 10
    private Long size = 10L;
}
