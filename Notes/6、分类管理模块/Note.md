# 分类管理模块

## 一、需求分析

### 1.1、新增

- 前端新增分类表单模态框
- 后端新增分类接口

### 1.2、查询

- 前端查询类别数据渲染表格（可模糊查询类别名称）
- 后端分页查询类别接口

### 1.3、删除

- 前端删除确认框及操作
- 后端删除分类接口

### 1.4、所有分类的列表(在文章模块用到时再写)

- 前端发布文章要从所有分类中选择一个类别
- 后端不分页分类列表接口

## 二、前端分类管理页面样式布局

### 2.1、最终效果

![](images/0.png)

页面大致可分为两个部分，上面是搜索模块可按分类名称或创建日期搜索，下面是表格模块展示查询到的数据，每个部分都分别用一个卡片组件包裹。代码如下：

### 2.2、头部

```vue
<script setup>
import { Search, RefreshRight } from "@element-plus/icons-vue"
</script>

<template>
  <div>
    <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
    <el-card shadow="never" class="mb-5">
      <!-- flex 布局，内容垂直居中 -->
      <div class="flex items-center">
        <el-text>分类名称</el-text>
        <div class="ml-3 w-52 mr-5"><el-input placeholder="请输入（模糊查询）" /></div>

        <el-text>创建日期</el-text>
        <div class="ml-3 w-30 mr-5">
          <!-- 日期选择组件（区间选择） -->
          <el-date-picker type="daterange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" size="default" />
        </div>

        <el-button type="primary" class="ml-3" :icon="Search">查询</el-button>
        <el-button class="ml-3" :icon="RefreshRight">重置</el-button>
      </div>
    </el-card>
  </div>
</template>
```

### 2.3、表格部分

```html
<el-card shadow="never">
  <!-- 新增按钮 -->
  <div class="mb-5">
    <el-button type="primary">
      <el-icon class="mr-1">
        <Plus />
      </el-icon>
      新增</el-button>
  </div>

  <!-- 分页列表 -->
  <el-table :data="[]" border stripe style="width: 100%">
    <el-table-column prop="name" label="分类名称" width="180" />
    <el-table-column prop="createTime" label="创建时间" width="180" />
    <el-table-column label="操作" >
      <template #default="scope">
        <el-button type="danger" size="small">
          <el-icon class="mr-1">
            <Delete />
          </el-icon>
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <div class="mt-10 flex justify-center">
    <el-pagination
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50]"
      :small="false"
      :background="true"
      :total="50" />
  </div>
</el-card>
```

### 2.4、组件中文化

修改`App.vue`文件内容，配置中文：

```vue
<script setup>
import zhCn from "element-plus/dist/locale/zh-cn"

const locale = zhCn
</script>

<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<style>
#nprogress .bar {
  background: #409eff !important;
}
</style>
```

## 三、新增分类功能开发

### 3.1、新增分类接口开发

#### 3.1.0、目标

向服务器`/admin/category/add`发送请求，入参如下：

```json
{
  "name": "要新增的分类名称"
}
```

名称填写不规范时返回如下：

```json
{
  "success": false,
  "message": "分类名称字数限制在 1 ~ 10 之间，当前值：输入的分类名称值",
  "code": "10001",
  "data": null
}
```

添加成功的情况：

```json
{
  "success": true,
  "message": null,
  "code": null,
  "data": null
}
```

#### 3.1.1、建表

```sql
CREATE TABLE `t_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '分类名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文章分类表';
```

#### 3.1.2、创建对应的 DO 类

在`weblog-module-common`模块中的`/domain/dos`包下，创建`CategoryDO`实体类，字段与表中字段一一对应：

```java
package com.cm.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_category")
public class CategoryDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private Boolean isDeleted;
}

```

#### 3.1.3、创建对应的 mapper

在`/domain/mapper`包下，创建`CategoryMapper`接口，新建根据名称查询类别的默认方法`selectByName`：

```java
package com.cm.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.weblog.common.domain.dos.CategoryDO;

public interface CategoryMapper extends BaseMapper<CategoryDO> {
    /**
     * 根据名称查询类别
     * @param name 名称
     * @return 分类
     */
    default CategoryDO selectByName(String name) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, name);

        return selectOne(wrapper);
    }
}

```

#### 3.1.4、创建入参实体类

在 weblog-module-admin 的 pom.xml 文件中添加如下依赖：

```xml
<dependency>
		<groupId>org.hibernate.validator</groupId>
		<artifactId>hibernate-validator</artifactId>
</dependency>
```

编辑`weblog-module-admin`子模块，在`vo`包下，新增`category`包，后续所有和分类相关的`VO`实体类均放在此包下，然后创建`AddCategoryReqVO`入参实体类，代码如下：

```java
package com.cm.weblog.admin.model.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加分类入参")
public class AddCategoryVO {
    @NotBlank(message = "分类名称不能为空")
    @Length(min = 1, max = 10, message = "分类名称字数限制在 1 ~ 10 之间")
    private String name;
}

```

#### 3.1.5、添加分类服务及其实现类

在`service`包下创建`AdminCategoryService`接口，统一规划分类服务的功能，在该接口中添加新增分类的方法`addCategory`：

```java
package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.common.utils.Response;

public interface AdminCategoryService {
    Response<?> addCategory(AddCategoryReqVO addCategoryReqVO);
}

```

接口规范完成后向`RespondCodeEnum`中添加重复添加分类的枚举值：

```java
CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！")
```

然后在`impl`包下创建对应的实现类`AdminCategoryServiceImpl`，实现刚才添加的方法：

```java
package com.cm.weblog.admin.service.impl;

import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.admin.service.AdminCategoryService;
import com.cm.weblog.common.domain.dos.CategoryDO;
import com.cm.weblog.common.domain.mapper.CategoryMapper;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.exception.BizException;
import com.cm.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Response<?> addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();

        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);

        if (Objects.nonNull(categoryDO)) {
            log.warn("分类名称：{} 已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(categoryName.trim())
                .build();

        categoryMapper.insert(insertCategoryDO);

        return Response.success();
    }
}

```

#### 3.1.6、控制层中添加接口

在`controller`包下创建`AdminCategoryController`分类控制器，添加目标中的`/admin/category/add`接口：

```java
package com.cm.weblog.admin.controller;

import com.cm.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.cm.weblog.admin.service.AdminCategoryService;
import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {
    @Resource
    private AdminCategoryService adminCategoryService;

    @PostMapping("/category/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    public Response<?> addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVO) {
        return adminCategoryService.addCategory(addCategoryReqVO);
    }
}

```

#### 3.1.7、测试

**分类名称为空（字数不足）入参：**

```json
{
    "name": ""
}
```

返回：

```json
{
    "success": false,
    "message": "name 分类名称字数限制在 1 ~ 10 之间，当前值：’'；name 分类名称不能为空，当前值：’'；",
    "code": "10001",
    "data": null
}
```

**分类名称字数过多入参：**

```json
{
    "name": "012345678901234567890123"
}
```

返回：

```json
{
    "success": false,
    "message": "name 分类名称字数限制在 1 ~ 10 之间，当前值：’012345678901234567890123'；",
    "code": "10001",
    "data": null
}
```

**成功入参**：

```json
{
    "name": "Java"
}
```

返回：

```json
{
    "success": true,
    "message": null,
    "code": null,
    "data": null
}
```

**分类名称重复入参：**

```json
{
    "name": "Java"
}
```

返回：

```json
{
    "success": false,
    "message": "该分类已存在，请勿重复添加！",
    "code": "20005",
    "data": null
}
```

### 3.2、新增分类前端部分开发

#### 3.2.1、模态框及表单样式布局

给新增分类按钮绑定点击事件，点击后展示模态框：

```vue
<script setup>
import { ref } from "vue"

const dialogVisible = ref(false)
</script>

<template>
  <!-- 省略其它 -->

  <!-- 新增按钮 -->
  <div class="mb-5">
    <el-button type="primary" @click="dialogVisible = true">
      <el-icon class="mr-1">
        <Plus />
      </el-icon>
      新增</el-button>
  </div>

  <el-dialog v-model="dialogVisible" title="添加文章分类" width="40%" :draggable ="true" :close-on-click-modal="false" :close-on-press-escape="false"></el-dialog>
</template>
```

向模态框中添加表单以及绑定表单变量：

```vue
<script setup>
import { ref, reactive } from "vue"

const form = reactive({
  name: ""
})

const formRef = ref(null)

const rules = {
  name: [
    {
      required: true,
      message: "分类名称不能为空",
      trigger: "blur"
    },
    {
      min: 1,
      max: 10,
      message: "分类名称字数要求大于 1 个字符，小于 10 个字符",
      trigger: "blur"
    }
  ]
}
</script>

<template>
  <!-- 省略其它 -->
  <el-dialog v-model="dialogVisible" title="添加文章分类" width="40%" :draggable ="true" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form ref="formRef" :rules="rules" :model="form">
      <el-form-item label="分类名称" prop="name" label-width="80px">
        <!-- 输入框组件 -->
        <el-input size="large" v-model="form.name" placeholder="请输入分类名称" maxlength="10" show-word-limit clearable/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
```

#### 3.2.2、功能开发

**封装请求**

在`/api/admin`目录下新建`category.js`，用来统一存放分类相关的请求，并在该文件中添加新增分类的请求：

```js
import axios from "@/utils/axios"

/**
 * 添加分类
 * @param data 分类
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function addCategory(data) {
  return axios.post("/admin/category/add", data)
}
```

**处理表单提交**

完善刚才提交按钮绑定的函数：

```js
function onSubmit() {
  formRef.value.validate(async (valid) => {
    if (valid) {
      const { success, message } = await addCategory(form)
      if (success) {
        showMessage("添加成功")
        dialogVisible.value = false
        form.name = ""

        // 渲染表格数据，暂未完成接口
      } else {
        showMessage(message, "error")
      }
    }
  })
}
```

## 四、分页查询功能开发

### 4.1、接口开发

#### 4.1.1、设计接口模型

**定义入参格式：**

```json
{
    "current": 1, // 页码
    "size": 10, // 每页的数据量
    "name": "", // 要模糊查询的分类名称
    "startDate": "" // 要搜索的创建时间起始值
    "endDate": "" // 要搜索的创建时间截止值
}
```

**定义响应格式：**

```json
{
    "success": true,
    "message": null,
    "code": null,
    "data": [
        {
            "id": 1, // ID
            "name": "分类名称",
            "createTime": "xxxx-xx-xx xx:xx:xx"
        }
    ]
}
```

#### 4.1.2、从总响应类中封装派生的分页响应类

在`weblog-module-common`模块下的`utils`包中新建`PageResponse`类继承`Response`，根据所设计的响应模型添加分页请求的成功响应：

```java
package com.cm.weblog.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * 分页响应类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponse<T> extends Response<T> {
    // 总数
    private long total = 0L;

    // 每页数据量，默认10
    private long size = 10L;

    // 当前页码
    private long current;

    // 总页数
    private long pages;

    /**
     * 成功响应
     * @param page 分页
     * @param data 响应数据
     * @return 响应对象
     * @param <T> 传入的数据类型
     */
    public static <T, D> PageResponse<T> success(IPage<D> page, T data) {
        PageResponse<T> response = new PageResponse<>();

        response.setSuccess(true);
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setData(data);

        return response;
    }
}

```

#### 4.1.3、封装分页请求基础参数类

在`weblog-module-common`模块下新建`model`包，在该包下新建分页请求基础数据类`BasePageQuery`，存放分页请求所需的公共字段：

```java
package com.cm.weblog.common.model;

import lombok.Data;

/**
 * 分页请求基础类
 */
@Data
public class BasePageQuery {
    // 当前页码，默认 1
    private Long current = 1L;

    // 每页数据量，默认 10
    private Long size = 10L;
}

```

#### 4.1.4、配置 MybatisPlus 分页插件

在`weblog-module-common`模块下的`MybatisPlusConfig`配置类中添加分页插件：

```java
package com.cm.weblog.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.cm.weblog.common.domain.mapper")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}

```

#### 4.1.5、封装出入参 VO

在`weblog-module-admin`模块的`/model/vo/category`包下新建分页查询分类接口的出人参`VO`

**入参`FindCatgeoryPageListReqVO`：**

```java
package com.cm.weblog.admin.model.vo.category;

import com.cm.weblog.common.model.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 分页查询分类入参实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "分页查询分类接口数据人参实体类")
public class FindCategoryPageListReqVO extends BasePageQuery {
    // 名称
    private String name;

    // 创建日期起始值
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 注解将"yyyy-MM-dd HH:mm:ss"这种形式的时间日期字符串解析为LocalDateTime
    private LocalDateTime startDate;

    // 创建日期截止值
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 注解将"yyyy-MM-dd HH:mm:ss"这种形式的时间日期字符串解析为LocalDateTime
    private LocalDateTime endDate;
}
```

**响应`FindCategoryPageListRspVO`：**

```java
package com.cm.weblog.admin.model.vo.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 分页查询分类响应实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryPageListRspVO {
    // ID
    private Long id;

    // 名称
    private String name;

    // 创建时间
    private LocalDateTime createTime;
}

```

#### 4.1.6、在 service 层定义业务方法及实现

在`weblog-module-admin`的`service`包中的`AdminCategoryService`服务中添加分页查询分类的方法：

```java
/**
 * 分页查询分类
 * @param findCategoryPageListReqVO 请求入参
 * @return 请求响应数据
*/
PageResponse<List<FindCategoryPageListRspVO>> findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO);
```

在`impl`包中的`AdminCategoryService`中实现这个方法：

```java
@Override
public PageResponse<List<FindCategoryPageListRspVO>> findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
    // 1、获取页码信息
    Long current = findCategoryPageListReqVO.getCurrent();
    Long size = findCategoryPageListReqVO.getSize();

    // 2、构建分页对象
    Page<CategoryDO> page = new Page<>(current, size);

    // 3、构建查询条件
    LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();

    String name = findCategoryPageListReqVO.getName();
    LocalDateTime startDate = findCategoryPageListReqVO.getStartDate();
    LocalDateTime endDate = findCategoryPageListReqVO.getEndDate();

    wrapper
            .like(StringUtils.isNotBlank(name), CategoryDO::getName, name.trim())
            .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate)
            .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)
            .orderByDesc(CategoryDO::getCreateTime);

    // 4、执行分页查询
    Page<CategoryDO> categoryDOPage = categoryMapper.selectPage(page, wrapper);
    List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

    // DO 转 VO
    List<FindCategoryPageListRspVO> vos = Collections.emptyList();
    if (!CollectionUtils.isEmpty(categoryDOS)) {
        vos = categoryDOS.stream()
                .map(categoryDO -> FindCategoryPageListRspVO.builder()
                        .id(categoryDO.getId())
                        .name(categoryDO.getName())
                        .createTime(categoryDO.getCreateTime())
                        .build())
                .collect(Collectors.toList());
    }

    return PageResponse.success(categoryDOPage, vos);
}
```

#### 4.1.7、在 controller 层定义接口

向`AdminCategoryController`中添加分页查询分类的请求：

```java
@PostMapping("/category/list")
@ApiOperation(value = "分页查询分类数据")
@ApiOperationLog(description = "分页查询分类数据")
public PageResponse<List<FindCategoryPageListRspVO>> findCategoryList(@RequestBody @Validated FindCategoryPageListReqVO findCategoryPageListReqVO) {
	return adminCategoryService.findCategoryList(findCategoryPageListReqVO);
}
```

#### 4.1.8、测试

**无查询条件**

入参：

```java
{
    "current": 1,
    "size": 10,
    "name": "",
    "startDate": "",
    "endDate": ""
}
```

响应：

```json
{
    "success": true,
    "message": null,
    "code": null,
    "data": [
        {
            "id": 1,
            "name": "Java",
            "createTime": "2025-10-29 21:27:57"
        }
    ],
    "total": 1,
    "size": 10,
    "current": 1,
    "pages": 1
}
```

**超出数据范围**

入参：

```json
{
    "current": 2,
    "size": 10,
    "name": "",
    "startDate": "",
    "endDate": ""
}
```

响应：

```json
{
    "success": true,
    "message": null,
    "code": null,
    "data": [],
    "total": 1,
    "size": 10,
    "current": 2,
    "pages": 1
}
```

**未查询到数据**

入参：

```json
{
    "current": 1,
    "size": 10,
    "name": "测试",
    "startDate": "",
    "endDate": ""
}
```

响应：

```json
{
    "success": true,
    "message": null,
    "code": null,
    "data": [],
    "total": 0,
    "size": 10,
    "current": 1,
    "pages": 0
}
```

### 4.2、前端分类列表数据渲染

#### 4.2.1、封装请求

在`/api/admin/category.js`文件中添加分页查询分类的请求：

```js
/**
 * 分页查询分类接口
 * @param data 页码、每页数据量、模糊查询的名称、时间范围等参数
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function getCategoryPageList(data) {
  return axios.post("/admin/category/list", data)
}
```

#### 4.2.2、前端发送请求

首先安装处理时间的依赖：

```shell
pnpm i moment
```

然后把`CategoryList.vue`页面搜索表单变量绑定以及需要发送请求的部分补充完整，最终代码如下：

```vue
<script setup>
import { Search, RefreshRight } from "@element-plus/icons-vue"
import {
  ref,
  reactive
} from "vue"
import { addCategory, getCategoryPageList } from "@/api/admin/category"
import { showMessage } from "@/utils/message"

import moment from "moment"

const dialogVisible = ref(false)

const form = reactive({
  name: ""
})

const formRef = ref(null)

const rules = {
  name: [
    {
      required: true,
      message: "分类名称不能为空",
      trigger: "blur"
    },
    {
      min: 1,
      max: 10,
      message: "分类名称字数要求大于 1 个字符，小于 10 个字符",
      trigger: "blur"
    }
  ]
}

function onSubmit() {
  formRef.value.validate(async (valid) => {
    if (valid) {
      const { success, message } = await addCategory(form)
      if (success) {
        showMessage("添加成功")
        dialogVisible.value = false
        form.name = ""

        // 渲染表格数据
        await getTableData()
      } else {
        showMessage(message, "error")
      }
    }
  })
}

const shortcuts = [
  {
    text: "最近一周",
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    },
  },
  {
    text: '最近一个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    },
  },
  {
    text: '最近三个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    },
  }
]

const pickedDate = ref(null)
const searchCategoryName = ref("")
const current = ref(1)
const total = ref(0)
const size = ref(10)
const tableData = ref([])
const isTableLoading = ref(false)

async function getTableData(p = current.value) {
  console.log(p)
  current.value = p
  try {
    let startDate = ""
    let endDate = ""
    if (pickedDate.value) {
      startDate = moment(pickedDate.value[0]).format("YYYY-MM-DD HH:mm:ss")
      endDate = moment(pickedDate.value[1]).format("YYYY-MM-DD HH:mm:ss")
    }
    const { data, current: currentPage, success, size: pageSize, total: totalCount } = await getCategoryPageList({
      current: current.value,
      size: size.value,
      name: searchCategoryName.value,
      startDate,
      endDate,
    })
    if (success) {
      tableData.value = data
      current.value = currentPage
      size.value = pageSize
      total.value = totalCount
    }
  } catch(e) {
    console.log(e)
  }
}

getTableData()

function handleSizeChange(chosenSize) {
  size.value = chosenSize
  getTableData(1)
}

function resetQueryParams() {
  searchCategoryName.value = ""
  pickedDate.value = null
}
</script>

<template>
  <div>
    <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
    <el-card shadow="never" class="mb-5">
      <!-- flex 布局，内容垂直居中 -->
      <div class="flex items-center">
        <el-text>分类名称</el-text>
        <div class="ml-3 w-52 mr-5"><el-input placeholder="请输入（模糊查询）" v-model="searchCategoryName" /></div>

        <el-text>创建日期</el-text>
        <div class="ml-3 w-30 mr-5">
          <!-- 日期选择组件（区间选择） -->
          <el-date-picker :shortcuts="shortcuts" v-model="pickedDate" type="daterange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" size="default" />
        </div>

        <el-button type="primary" class="ml-3" :icon="Search" @click="getTableData(1)">查询</el-button>
        <el-button class="ml-3" :icon="RefreshRight" @click="resetQueryParams">重置</el-button>
      </div>
    </el-card>

    <el-card shadow="never">
      <!-- 新增按钮 -->
      <div class="mb-5">
        <el-button type="primary" @click="dialogVisible = true">
          <el-icon class="mr-1">
            <Plus />
          </el-icon>
          新增</el-button>
      </div>

      <!-- 分页列表 -->
      <el-table v-loading="isTableLoading" :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="name" label="分类名称" width="180" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" >
          <template #default="scope">
            <el-button type="danger" size="small">
              <el-icon class="mr-1">
                <Delete />
              </el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="mt-10 flex justify-center">
        <el-pagination
            layout="total, sizes, prev, pager, next, jumper"
            :page-sizes="[10, 20, 50]"
            :small="false"
            :background="true"
            :total="total"
            v-model:current-page="current"
            v-model:page-size="size"
            @size-change="handleSizeChange"
            @current-change="getTableData"
        />
      </div>

    </el-card>

    <el-dialog v-model="dialogVisible" title="添加文章分类" width="40%" :draggable ="true" :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="formRef" :rules="rules" :model="form">
        <el-form-item label="分类名称" prop="name" label-width="80px" class="align-middle">
          <!-- 输入框组件 -->
          <el-input size="large" v-model="form.name" placeholder="请输入分类名称" maxlength="10" show-word-limit clearable/>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="onSubmit">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
```

## 五、删除分类功能

### 5.1、接口开发

#### 5.1.1、设计接口模型

- 接口地址：`/admin/category/delete`
- 接口方法：`DELETE`
- 入参：`/admin/category/delete?id={要删除的分类的id}`
- 响应：
  ```json
  {
    "success": true,
    "message": null,
    "code": null,
    "data": null
  }
  ```

#### 5.1.2、在业务层定义方法及实现

首先向`weblog-module-admin`中的`AdminCategoryService`中添加删除分类的方法定义：

```java
/**
 * 删除分类
 * @param id 分类id
 * @return 响应
 */
Response<?> deleteCategory(Long id);
```

然后在实现类中实现这个方法，调用分类的`mapper`删除对应`id`的分类：

```java
@Override
public Response<?> deleteCategory(Long id) {
    categoryMapper.deleteById(id);
    return Response.success();
}
```

#### 5.1.3、在控制层添加接口

向`AdminCategoryController`控制器中添加删除分类的接口：

```java
@DeleteMapping("/category/delete")
@ApiOperation(value = "删除分类")
@ApiOperationLog(description = "删除分类")
public Response<?> findCategoryPageList(@RequestParam Long id) {
    return adminCategoryService.deleteCategory(id);
}
```

#### 5.1.4、测试

重启项目，发送请求，查看效果

请求：`admin/category/delete?id=11`

返回：

```json
{
  "success": true,
  "message": null,
  "code": null,
  "data": null
}
```

数据库中`id`为`11`的数据成功被删除。

### 5.2、前端开发

#### 5.2.1、封装请求

向`/api/admin/category.js`文件中添加删除分类的请求：

```js
/**
 * 删除分类接口
 * @param id 分类 id
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function deleteCategory(id) {
  return axiso({
    url: "/admin/category/delete",
    method: "DELETE",
    params: {
      id
    }
  })
}
```

#### 5.2.2、为删除按钮绑定点击事件

点击删除按钮后首先弹出确认框，点击确认后再发送删除分类的请求，删除成功后再次调用接口渲染新的表格数据：

```vue
<script setup>
// ...省略
import { showModel } from "@/utils/model"

function showDeleteCategoryConfirmModal(r) {
  showModel('是否确定要删除该分类？').then(async () => {
    try {
      const { success, message } = await deleteCategory(r.id)
      if (success) {
        showMessage('删除成功')
        getTableData()
      } else {
        showMessage(message, "error")
      }
    } catch(e) {
      console.log(e)
    }
  }).catch(() => {
    console.log('取消了')
  })
}
</script>

<template>
  <!-- 省略 -->
  <el-button type="danger" size="small" @click="showDeleteCategoryConfirmModal(scope.row)">
    <el-icon class="mr-1">
      <Delete />
    </el-icon>
    删除
  </el-button>
</template>
```

## 六、前端优化

### 6.1、封装通用表单模态框组件

#### 6.1.1、封装组件

用户退出登录和新增分类等地方多次弹出对话框内置表单项，将公共部分抽象出来封装成一个模态框组件

在`src`目录下新建`components`目录，统一存放自定义组件，在其中新建`FormDialog.vue`：

```vue
<script setup>
import { ref } from "vue"

defineProps({
  title: String,
  width: {
    type: String,
    default: "40%"
  },
  destroyOnClose: {
    type: Boolean,
    default: false
  },
  confirmText: {
    type: String,
    default: "提交"
  }
})

const dialogVisible = ref(false)

function open() {
  dialogVisible.value = true
}

function close() {
  dialogVisible.value = false
}

defineExpose({
  open,
  close
})

const emit = defineEmits(["submit"])

function submit() {
  emit("submit")
}
</script>

<template>
  <el-dialog
      v-model="dialogVisible"
      :title="title"
      :width="width"
      :destroy-on-close="destroyOnClose"
  >
    <!-- 插槽 -->
    <slot></slot>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
```

#### 6.1.2、分类列表页替换为自定义表单模态框组件

删除`CategoryList.vue`中原先使用的模态框代码，使用自定义的组件，整理代码，删除多余变量：

```vue
<script setup>
// ... 省略
import FormDialog from "@/components/FormDialog.vue"

const formDialogRef = ref(null)

function openDialog() {
  formDialogRef.value.open()
}

function onSubmit() {
  formRef.value.validate(async (valid) => {
    if (valid) {
      const { success, message } = await addCategory(form)
      if (success) {
        showMessage("添加成功")
        formDialogRef.value.close()
        form.name = ""

        // 渲染表格数据
        await getTableData()
      } else {
        showMessage(message, "error")
      }
    }
  })
}
</script>

<template>
  <!-- 省略 -->

  <!-- 新增按钮 -->
  <div class="mb-5">
    <el-button type="primary" @click="openDialog">
      <el-icon class="mr-1">
        <Plus />
      </el-icon>
      新增
    </el-button>
  </div>

  <FormDialog ref="formDialogRef" title="添加文章分类" width="40%" @submit="onSubmit">
    <el-form ref="formRef" :rules="rules" :model="form">
      <el-form-item label="分类名称" prop="name" label-width="80px" class="align-middle">
        <!-- 输入框组件 -->
        <el-input size="large" v-model="form.name" placeholder="请输入分类名称" maxlength="10" show-word-limit clearable/>
      </el-form-item>
    </el-form>
  </FormDialog>
</template>
```

#### 6.1.3、用户修改密码替换为自定义表单模态框组件

修改`AdminHeader.vue`，最终代码如下：

```vue
<script setup>
import AvatarImg from "@/assets/images/avatar.jpg"

import { useMenuStore } from "@/stores/menu"
import { useUserStore } from "@/stores/user.js"
import { useRouter } from "vue-router"
import { useFullscreen } from "@vueuse/core"
import { ref, reactive, watch } from "vue"
import { updatePassword } from "@/api/admin/user"
import { showMessage } from "@/utils/message"
import { showModel } from "@/utils/model"
import FormDialog from "@/components/FormDialog.vue"

const menuStore = useMenuStore()
const userStore = useUserStore()
const router = useRouter()
const { isFullscreen, toggle } = useFullscreen()

function handleRefresh() {
  location.reload()
}

const formDialogRef = ref(null)
const formRef = ref(null)
const form = reactive({
  username: userStore.userInfo.username || '',
  password: '',
  rePassword: ''
})
const rules = {
  username: [
    {
      required: true,
      message: '用户名不能为空',
      trigger: 'blur'
    }
  ],
  password: [
    {
      required: true,
      message: '密码不能为空',
      trigger: 'blur',
    },
  ],
  rePassword: [
    {
      required: true,
      message: '确认密码不能为空',
      trigger: 'blur',
    },
  ]
}
watch(() => userStore.userInfo.username, v => { form.username = v })
function handleDropdownCommand(command) {
  if (command === "updatePassword") {
    formDialogRef.value.open()
  }

  if (command === "logout") {
    showModel("是否确认要退出登录？").then(() => {
      userStore.logout()
      showMessage("退出登录成功！")
      router.push("/login")
    })
  }
}

function onSubmit() {
  formRef.value.validate(async valid => {
    if (valid) {
      if (form.password !== form.rePassword) {
        return showMessage("两次密码输入不一致，请检查！", "warning")
      }

      try {
        const { success, message } = await updatePassword(form)
        if (success) {
          showMessage("密码重置成功，请重新登录！")

          userStore.logout()

          formDialogRef.value.close()
          router.push('/login')
        } else {
          showMessage(message, "error")
        }
      } catch(e) {
        console.log(e)
      } finally {
      }
    }
  })
}
</script>

<template>
  <!-- 通过 flex 指定水平布局 -->
  <!-- 设置背景色为白色、高度为 64px，padding-right 为 4， border-bottom 为 slate 200 -->
  <div class="bg-white h-[64px] flex pr-4 border-b border-slate-100">
    <!-- 左边栏收缩、展开 -->
    <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200" @click="menuStore.toggleMenuCollapsed">
      <el-icon>
        <Fold v-if="!menuStore.isMenuCollapsed" />
        <Expand v-else />
      </el-icon>
    </div>

    <!-- 右边容器，通过 ml-auto 让其在父容器的右边 -->
    <div class="ml-auto flex">
      <!-- 点击刷新页面 -->
      <el-tooltip class="box-item" effect="dark" content="刷新" placement="bottom">
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200" @click="handleRefresh">
          <el-icon>
            <Refresh />
          </el-icon>
        </div>
      </el-tooltip>
      <!-- 点击全屏展示 -->
      <el-tooltip class="box-item" effect="dark" :content="isFullscreen ? '取消全屏' : '全屏'" placement="bottom">
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 mr-2 hover:bg-gray-200" @click="toggle">
          <el-icon>
            <FullScreen v-if="!isFullscreen"/>
            <Aim v-else/>
          </el-icon>
        </div>
      </el-tooltip>

      <!-- 登录用户头像 -->
      <el-dropdown trigger="click" class="flex items-center justify-center" @command="handleDropdownCommand">
        <span class="el-dropdown-link flex items-center justify-center text-gray-700 text-xs">
          <!-- 头像 Avatar -->
          <el-avatar class="mr-2" :size="25" :src="AvatarImg" />
          {{ userStore.userInfo.username }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

  <FormDialog ref="formDialogRef" title="修改密码" width="40%" @submit="onSubmit">
    <el-form ref="formRef" :rules="rules" :model="form">
      <el-form-item label="用户名" prop="username" label-width="120px">
        <!-- 输入框组件 -->
        <el-input size="large" v-model="form.username" placeholder="请输入用户名" clearable disabled />
      </el-form-item>
      <el-form-item label="密码" prop="password" label-width="120px">
        <el-input size="large" type="password" v-model="form.password" placeholder="请输入密码" clearable show-password />
      </el-form-item>
      <el-form-item label="确认密码" prop="rePassword" label-width="120px">
        <el-input size="large" type="password" v-model="form.rePassword" placeholder="请确认密码" clearable show-password />
      </el-form-item>
    </el-form>
  </FormDialog>
</template>

<style scoped>
.el-dropdown-link {
  outline: none;
}
:deep(.el-input.is-disabled .el-input__inner) {
  background-color: transparent;
}
</style>

```

### 6.2、等待请求完成时加载中状态

修改`CategoryList.vue`，为`table`组件绑定加载中切换的状态，当请求开始时展示加载动画，请求结束后结束动画：

```vue
<script setup>
// ... 省略
const isTableLoading = ref(false)

async function getTableData(p = current.value) {
  current.value = p
  isTableLoading.value = true
  try {
    let startDate = ""
    let endDate = ""
    if (pickedDate.value) {
      startDate = moment(pickedDate.value[0]).format("YYYY-MM-DD HH:mm:ss")
      endDate = moment(pickedDate.value[1]).format("YYYY-MM-DD HH:mm:ss")
    }
    const { data, current: currentPage, success, size: pageSize, total: totalCount } = await getCategoryPageList({
      current: current.value,
      size: size.value,
      name: searchCategoryName.value,
      startDate,
      endDate,
    })
    if (success) {
      tableData.value = data
      current.value = currentPage
      size.value = pageSize
      total.value = totalCount
    }
  } catch(e) {
    console.log(e)
  } finally {
    isTableLoading.value = false
  }
}
</script>

<template>
  <!-- 省略 -->
  <el-table v-loading="isTableLoading" :data="tableData" border stripe style="width: 100%"></el-table>
</template>
```

修改`FormDialog`组件，暴露切换加载状态的方法：

```vue
<script setup>
// ... 省略
const isLoading = ref(false)

function switchLoading() {
  isLoading.value = !isLoading.value
}

defineExpose({
  switchLoading
})
</script>

<template>
  <!-- 省略 -->
  <el-button type="primary" @click="submit" :loading="isLoading">
    提交
  </el-button>
</template>
```

修改使用该组件的页面，在提交表单时以及表单切换时切换加载状态：

```js
function onSubmit() {
  formRef.value.validate(async (valid) => {
    if (valid) {
      formDialogRef.value.switchLoading()
      try {
        // ...执行过程略
      } catch(e) {
        console.log(e)
      } finally {
        formDialogRef.value.switchLoading()
      }
    }
  })
}
```