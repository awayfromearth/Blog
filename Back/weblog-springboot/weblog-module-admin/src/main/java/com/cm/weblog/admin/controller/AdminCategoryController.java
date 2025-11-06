package com.cm.weblog.admin.controller;

import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.cm.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.cm.weblog.admin.service.AdminCategoryService;
import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.utils.PageResponse;
import com.cm.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {
    @Resource
    private AdminCategoryService adminCategoryService;

    @PostMapping("/category/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<?> addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVO) {
        return adminCategoryService.addCategory(addCategoryReqVO);
    }

    @PostMapping("/category/list")
    @ApiOperation(value = "分页查询分类数据")
    @ApiOperationLog(description = "分页查询分类数据")
    public PageResponse<List<FindCategoryPageListRspVO>> findCategoryList(@RequestBody @Validated FindCategoryPageListReqVO findCategoryPageListReqVO) {
        return adminCategoryService.findCategoryList(findCategoryPageListReqVO);
    }

    @DeleteMapping("/category/delete")
    @ApiOperation(value = "删除分类")
    @ApiOperationLog(description = "删除分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<?> deleteCategory(@RequestParam Long id) {
        return adminCategoryService.deleteCategory(id);
    }
}
