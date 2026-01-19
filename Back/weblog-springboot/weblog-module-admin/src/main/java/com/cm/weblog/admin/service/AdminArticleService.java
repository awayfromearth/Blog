package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.cm.weblog.common.utils.Response;

public interface AdminArticleService {
    /**
     * 发布文章服务
     * @param publishArticleReqVO 入参
     * @return 请求响应
     */
    Response<?> publishArticle(PublishArticleReqVO publishArticleReqVO);
}
