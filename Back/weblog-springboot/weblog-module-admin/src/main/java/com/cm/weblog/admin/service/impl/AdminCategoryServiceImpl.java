package com.cm.weblog.admin.service.impl;

import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.admin.service.AdminCategoryService;
import com.cm.weblog.common.domain.dos.CategoryDO;
import com.cm.weblog.common.domain.mapper.CategoryMapper;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.exception.BizException;
import com.cm.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
}
