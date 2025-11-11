package com.cm.weblog.admin.controller;

import com.cm.weblog.admin.model.vo.tag.AddTagReqVO;
import com.cm.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.cm.weblog.admin.model.vo.tag.FindTagPageListRspVO;
import com.cm.weblog.admin.service.AdminTagService;
import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.utils.PageResponse;
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
import java.util.List;

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

    @PostMapping("tag/list")
    @ApiOperation("分页查询标签")
    @ApiOperationLog(description = "分页查询标签")
    public PageResponse<List<FindTagPageListRspVO>> findTagList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
        return adminTagService.findTagPageList(findTagPageListReqVO);
    }
}
