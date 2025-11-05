package com.cm.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.cm.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.cm.weblog.admin.service.AdminCategoryService;
import com.cm.weblog.common.domain.dos.CategoryDO;
import com.cm.weblog.common.domain.mapper.CategoryMapper;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.exception.BizException;
import com.cm.weblog.common.utils.PageResponse;
import com.cm.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Response<?> addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();

        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);

        if (Objects.nonNull(categoryDO)) {
            log.warn("分类名称：{} 已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(categoryName.trim())
                .build();

        categoryMapper.insert(insertCategoryDO);

        return Response.success();
    }

    @Override
    public PageResponse<List<FindCategoryPageListRspVO>> findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        // 1、获取页码信息
        Long current = findCategoryPageListReqVO.getCurrent();
        Long size = findCategoryPageListReqVO.getSize();

        // 2、构建分页对象
        Page<CategoryDO> page = new Page<>(current, size);

        // 3、构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();

        String name = findCategoryPageListReqVO.getName();
        LocalDateTime startDate = findCategoryPageListReqVO.getStartDate();
        LocalDateTime endDate = findCategoryPageListReqVO.getEndDate();

        wrapper
                .like(StringUtils.isNotBlank(name), CategoryDO::getName, name.trim())
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)
                .orderByDesc(CategoryDO::getCreateTime);

        // 4、执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPage(page, wrapper);
        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

        // 5、DO 转 VO
        List<FindCategoryPageListRspVO> vos = Collections.emptyList();
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos = categoryDOS.stream()
                .map(categoryDO -> FindCategoryPageListRspVO.builder()
                        .id(categoryDO.getId())
                        .name(categoryDO.getName())
                        .createTime(categoryDO.getCreateTime())
                        .build())
                .collect(Collectors.toList());
        }

        return PageResponse.success(categoryDOPage, vos);
    }

    @Override
    public Response<?> deleteCategory(Long id) {
        categoryMapper.deleteById(id);

        return Response.success();
    }
}
