package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.cm.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.cm.weblog.common.utils.PageResponse;
import com.cm.weblog.common.utils.Response;

import java.util.List;

public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO 分类名称
     * @return 响应
     */
    Response<?> addCategory(AddCategoryReqVO addCategoryReqVO);

    /**
     * 分页查询分类
     * @param findCategoryPageListReqVO 请求入参
     * @return 请求响应数据
     */
    PageResponse<List<FindCategoryPageListRspVO>> findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO);
}
