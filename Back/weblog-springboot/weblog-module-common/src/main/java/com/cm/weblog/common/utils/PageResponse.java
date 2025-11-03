package com.cm.weblog.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * 分页响应类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponse<T> extends Response<T> {
    // 总数
    private long total = 0L;

    // 每页数据量，默认10
    private long size = 10L;

    // 当前页码
    private long current;

    // 总页数
    private long pages;

    /**
     * 成功响应
     * @param page 分页
     * @param data 响应数据
     * @return 响应对象
     * @param <T> 传入的数据类型
     */
    public static <T, D> PageResponse<T> success(IPage<D> page, T data) {
        PageResponse<T> response = new PageResponse<>();

        response.setSuccess(true);
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setData(data);

        return response;
    }
}
