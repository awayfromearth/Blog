package com.cm.weblog.admin.controller;

import com.cm.weblog.admin.model.vo.tag.AddTagReqVO;
import com.cm.weblog.admin.service.AdminTagService;
import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 标签模块")
public class AdminTagController {
    @Resource
    private AdminTagService adminTagService;

    @PostMapping("/tag/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<?> addTag(@RequestBody @Validated AddTagReqVO addTagReqVO) {
        return adminTagService.addTags(addTagReqVO);
    }
}
