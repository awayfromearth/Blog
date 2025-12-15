package com.cm.weblog.admin.controller;

import com.cm.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsReqVO;
import com.cm.weblog.admin.service.AdminBlogSettingsService;
import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/blog/settings")
@Api(tags = "Admin 博客设置模块")
public class AdminBlogSettingsController {
    @Resource
    private AdminBlogSettingsService adminBlogSettingsService;

    @GetMapping("/detail")
    @ApiOperation(value = "获取博客设置详情")
    @ApiOperationLog(description = "获取博客设置详情")
    public Response<?> findBlogSettingDetail() {
        return adminBlogSettingsService.findBlogSettingDetail();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新博客设置")
    @ApiOperationLog(description = "更新博客设置")
    public Response<?> updateBlogSettings(@RequestBody @Validated UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
        return adminBlogSettingsService.updateBlogSettings(updateBlogSettingsReqVO);
    }
}
