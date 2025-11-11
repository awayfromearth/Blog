package com.cm.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cm.weblog.common.domain.dos.TagDO;

import java.time.LocalDateTime;
import java.util.Objects;

public interface TagMapper extends BaseMapper<TagDO> {
    /**
     * 分页查询标签接口数据库查询
     * @param current 页码
     * @param size 每页数据量
     * @param name 标签名称
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @return 标签信息
     */
    default Page<TagDO> selectPageList(long current, long size, String name, LocalDateTime startDate, LocalDateTime endDate) {
        // 1、构造分页器
        Page<TagDO> page = new Page<>(current, size);

        // 2、构建查询条件
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                // 第一个字段用于当前端并不需要查询标签名称时略过这个条件
                .like(Objects.nonNull(name), TagDO::getName, name)
                .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate)
                .orderByDesc(TagDO::getCreateTime);

        // 3、执行数据库操作
        return selectPage(page, wrapper);
    }
}
