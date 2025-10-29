package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.common.utils.Response;

public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO 分类名称
     * @return 响应
     */
    Response<?> addCategory(AddCategoryReqVO addCategoryReqVO);
}
