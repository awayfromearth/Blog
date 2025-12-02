package com.cm.weblog.admin.controller;

import com.cm.weblog.admin.service.AdminBlogSettingsService;
import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 博客设置模块")
public class AdminBlogSettingsController {
    @Resource
    private AdminBlogSettingsService adminBlogSettingsService;

    @GetMapping("/blog/settings/detail")
    @ApiOperation(value = "获取博客设置详情")
    @ApiOperationLog(description = "获取博客设置详情")
    public Response<?> findBlogSettingDetail() {
        return adminBlogSettingsService.findBlogSettingDetail();
    }
}
