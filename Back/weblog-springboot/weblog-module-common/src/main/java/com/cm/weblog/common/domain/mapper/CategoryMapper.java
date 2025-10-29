package com.cm.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.weblog.common.domain.dos.CategoryDO;

public interface CategoryMapper extends BaseMapper<CategoryDO> {
    /**
     * 根据名称查询类别
     * @param name 名称
     * @return 分类
     */
    default CategoryDO selectByName(String name) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, name);

        return selectOne(wrapper);
    }
}
