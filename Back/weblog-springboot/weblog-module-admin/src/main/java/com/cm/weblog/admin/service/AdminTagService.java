package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.tag.AddTagReqVO;
import com.cm.weblog.common.utils.Response;

/**
 * 标签服务
 */
public interface AdminTagService {
    /**
     * 新增标签
     * @param addTagReqVO 标签名称集合
     * @return 请求响应
     */
    Response<?> addTags(AddTagReqVO addTagReqVO);
}
