package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.tag.AddTagReqVO;
import com.cm.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.cm.weblog.admin.model.vo.tag.FindTagPageListRspVO;
import com.cm.weblog.common.utils.PageResponse;
import com.cm.weblog.common.utils.Response;

import java.util.List;

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

    /**
     * 分页查询标签
     * @param findTagPageListReqVO 查询条件
     * @return 响应标签集合
     */
    PageResponse<List<FindTagPageListRspVO>> findTagPageList(FindTagPageListReqVO findTagPageListReqVO);
}
