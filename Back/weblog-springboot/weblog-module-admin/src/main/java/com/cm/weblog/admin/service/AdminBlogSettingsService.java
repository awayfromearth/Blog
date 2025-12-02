package com.cm.weblog.admin.service;

import com.cm.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
    /**
     * 博取博客设置详情
     * @return 响应
     */
    Response<?> findBlogSettingDetail();
}
