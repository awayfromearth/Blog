# 分类管理模块

## 一、需求分析

### 1.1、查询

- 前端查询类别数据渲染表格（可模糊查询类别名称）
- 后端分页查询类别接口

### 1.2、新增

- 前端新增分类表单模态框
- 后端新增分类接口

### 1.3、删除

- 前端删除确认框及操作
- 后端删除分类接口

### 1.4、所有分类的列表

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

#### 3.0、目标

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

