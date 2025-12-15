package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsReqVO;
import com.cm.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
    /**
     * 博取博客设置详情
     *
     * @return 响应
     */
    Response<?> findBlogSettingDetail();

    /**
     * 修改博客设置
     *
     * @param updateBlogSettingsReqVO 新博客设置
     * @return 响应请求
     */
    Response<?> updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);
}