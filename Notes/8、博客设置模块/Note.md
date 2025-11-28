# 博客设置模块

## 一、前置准备

### 1.1、数据库操作

#### 1.1.1、建表

```sql
CREATE TABLE `t_blog_settings` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(120) NOT NULL DEFAULT '' COMMENT '博客Logo',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '博客名称',
  `author` varchar(20) NOT NULL DEFAULT '' COMMENT '作者名',
  `introduction` varchar(120) NOT NULL DEFAULT '' COMMENT '介绍语',
  `avatar` varchar(120) NOT NULL DEFAULT '' COMMENT '作者头像',
  `github_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'GitHub 主页访问地址',
	`csdn_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'CSDN 主页访问地址',
  `gitee_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'Gitee 主页访问地址',
  `zhihu_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT '知乎主页访问地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='博客设置表';
```

#### 1.1.2、插入一条写死的数据

```sql
INSERT INTO `t_blog_settings` VALUES(1, 'https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg', 'CM 的博客', 'CM', '平安喜乐', 'https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg', '', '', '', '');
```

### 1.2、MINIO 安装及配置

#### 1.2.1、安装

下载地址：https://dl.minio.org.cn/server/minio/release/windows-amd64/minio.exe

文档地址：https://www.minio.org.cn/docs/minio/windows/index.html

本地安装目录：D:\application\Back-end Development\MINIO

#### 1.2.2、访问 MINIO 控制台

在安装目录先创建`data`目录用于存放数据，运行时指定这个目录：

```shell
minio.exe server D:\application\Back-end Development\MINIO\data
```

打开`http://127.0.0.1:9000`访问控制台

默认用户名：minioadmin；默认密码：minioadmin

![](images/0.png)

### 1.3、测试图片上传

#### 1.3.1、新建一个 Bucket

点击`Create a Bucket`按钮新建一个桶用于存储图片，指定`Bucket Name`为`weblog`，点击`Create Bucket`按钮

刷新`Buckets`列表，查看创建是否成功

![](images/1.png)

#### 1.3.2、修改权限

安装mc，下载地址：https://dl.minio.org.cn/client/mc/release/windows-amd64/mc.exe，将其转移到`minio.exe`同一目录下，连接minio控制台并指定别名：

```shell
mc.exe alias set myminio http://127.0.0.1:9000
```

设置`weblog`桶的权限为`public`：

```shell
mc anonymous set public myminio/weblog
```

![](images/2.png)

#### 1.3.3、上传图片

![](images/3.png)

点击`Upload File`，选择要上传的图片，成功后显示如下：

![](images/4.png)

访问http://127.0.0.1:9000/weblog/Snipaste_2025-10-09_21-55-31.png，图片可以正常显示，上传成功

## 二、需求分析

### 2.1、前端页面分析

目标效果如下：

![](images/5.png)

整体由卡片容器包裹，内含表单内容

表单可分为两个内容，从上往下依次为作者信息（包含图片上传组件），第三方链接跳转信息

### 2.2、接口分析

根据需求分析，该功能共需三个接口：

- 获取博客设置详情接口
- 更新博客设置接口
- 图片上传接口

## 三、前端静态页面搭建

### 3.1、卡片布局

编辑 `/pages/admin/BlogSetting.vue` 博客设置页，首先添加一个 `<el-card>` 卡片组件，用来包裹住里面的表单元素，代码如下：

```vue
<template>
  <el-card></el-card>
</template>
```

### 3.2、添加表单

向卡片组件中填充表单内容：

```vue
<script setup>
import { reactive, ref } from "vue"
import { Check, Close } from "@element-plus/icons-vue"

const formRef = ref()
const form = reactive({
  name: "",
  author: "",
  logo: "",
  avatar: "",
  introduction: "",
  githubHomePage: "",
  giteeHomepage: "",
  zhihuHomepage: "",
  csdnHomepage: "",
})
const rules = {
  name: [
    {
      required: true,
      message: "请输入博客名称",
      trigger: "blur"
    }
  ],
  author: [
    {
      required: true,
      message: "请输入作则名称",
      trigger: "blur"
    }
  ],
  logo: [
    {
      required: true,
      message: "请上传博客 LOGO",
      trigger: "blur"
    }
  ],
  avatar: [
    {
      required: true,
      message: "请上传作者头像",
      trigger: "blur"
    }
  ],
  introduction: [
    {
      required: true,
      message: "请输入介绍语",
      trigger: "blur"
    }
  ]
}
const isGithubChecked = ref(false)
const isGiteeChecked = ref(false)
const isZhihuChecked = ref(false)
const isCSDNChecked = ref(false)
</script>

<template>
  <el-card>
    <el-form ref="formRef" :model="form" label-width="150px" :rules="rules">
      <el-form-item label="博客名称" prop="name">
        <el-input v-model="form.name" clearable />
      </el-form-item>
      <el-form-item label="作者名" prop="author">
        <el-input v-model="form.author" clearable />
      </el-form-item>
      <el-form-item label="博客 LOGO" prop="logo">
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
        >
          <el-icon class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="作者头像" prop="avatar">
        <el-upload
            class="avatar-uploader"
            :show-file-list="false"
        >
          <el-icon class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="介绍语" prop="introduction">
        <el-input v-model="form.introduction" type="textarea" />
      </el-form-item>
      <el-form-item label="开启 GihHub 访问">
        <el-switch v-model="isGithubChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.giteeHomepage = ''" />
      </el-form-item>
      <el-form-item label="GitHub 主页访问地址" v-if="isGithubChecked">
        <el-input v-model="form.githubHomepage" clearable placeholder="请输入 GitHub 主页访问的 URL" />
      </el-form-item>
      <el-form-item label="开启 Gitee 访问">
        <el-switch v-model="isGiteeChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.giteeHomepage = ''" />
      </el-form-item>
      <el-form-item label="Gitee 主页访问地址" v-if="isGiteeChecked">
        <el-input v-model="form.giteeHomepage" clearable placeholder="请输入 Gitee 主页访问的 URL" />
      </el-form-item>
      <el-form-item label="开启知乎访问">
        <el-switch v-model="isZhihuChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.zhihuHomepage = ''" />
      </el-form-item>
      <el-form-item label="知乎主页访问地址" v-if="isZhihuChecked">
        <el-input v-model="form.zhihuHomepage" clearable placeholder="请输入知乎主页访问的 URL" />
      </el-form-item>
      <el-form-item label="开启 CSDN 访问">
        <el-switch v-model="isCSDNChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.csdnHomepage = ''" />
      </el-form-item>
      <el-form-item label="CSDN 主页访问地址" v-if="isCSDNChecked">
        <el-input v-model="form.csdnHomepage" clearable placeholder="请输入 CSDN 主页访问的 URL" />
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style scoped>
.el-card {
  width: 100%;
  max-height: calc(100vh - 148px);
  overflow: auto;
}

:deep(.avatar-uploader .avatar) {
  width: 100px;
  height: 100px;
  display: block;
}

:deep(.avatar-uploader .el-upload) {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

:deep(.avatar-uploader .el-upload:hover) {
  border-color: var(--el-color-primary);
}

:deep(.el-icon.avatar-uploader-icon) {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  text-align: center;
}
</style>
```

## 四、获取博客详情

### 4.1、接口开发

#### 4.1.1、接口设计

##### 4.1.1.1、定义接口模型

- 请求地址：`/admin/blog/settings/detail`

- 入参：无——数据库中仅保留一条数据，查询`ID`为`1`的数据即可

- 响应：

  ```json
  {
    "success": true,
    "message": null,
    "code": null,
    "data": {
      "logo": "https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg",
      "name": "犬小哈的博客test",
      "author": "小哈学Java",
      "introduction": "平安喜乐test",
      "avatar": "https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg",
      "githubHomepage": "",
      "csdnHomepage": "",
      "giteeHomepage": "",
      "zhihuHomepage": ""
    }
  }
  ```

##### 4.1.1.2、创建对应出入参 VO

在 `weblog-module-admin` 模块中创建 `/modle/vo/blogsettings` 包，在该包下创建名为 `FindBlogSettingsRspVO` 响应实体类，代码如下：

```java
package com.cm.weblog.admin.model.vo.blogSettings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindBlogSettingsRspVO {
    private String logo;

    private String name;

    private String author;

    private String introduction;

    private String avatar;

    private String githubHomepage;

    private String csdnHomepage;

    private String giteeHomepage;

    private String zhihuHomepage;
}

```

#### 4.1.2、整合MapStruct：简化属性映射

> ```java
> vos = categoryDOList.stream().map(categoryDO -> FindCategoryPageListRspVO.builder()
>                     .id(categoryDO.getId())
>                     .name(categoryDO.getName())
>                     .createTime(categoryDO.getCreateTime())
>                     .build())
>                     .collect(Collectors.toList());
> ```
>
> 在之前的代码中遇到`DO`与`VO`互相转换的情况常常是手动调用每个字段对应的方法实现属性映射。而当一个实例有大量字段时，这种方式会导致大段繁复的代码，为避免这种情况，可以使用`MapStruct`简化这个属性映射的过程

##### 4.1.2.1、添加依赖

向父项目`pom.xml`中添加`MapStruct`版本及插件信息：

```xml
<properties>
	<mapstruct.version>1.5.5.Final</mapstruct.version>
</properties>

<dependencyManagement>
	<dependencies>
    	<!-- Mapsturct 属性映射依赖 -->
        <dependency>
        	<groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>

<build>
	<pluginManagement>
    	<plugins>
        	<plugin>
            	<groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                	<source>${java.version}</source>
                    <target>${java.version}</target>
                    <annotationProcessorPaths>
                    	<path>
                        	<groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                        <path>
                        	<groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${mapstruct.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```

然后在`weblog-module-admin`中引入依赖：

```xml
<dependency>
	<groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
</dependency>
```

最后在`weblog-web`中添加编译插件：

```xml
<build>
    <plugins>
        <plugin>
        	<groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

##### 4.1.2.2、IDEA 配置 Mapstruct

首先启用注解处理器：

![](images/6.png)

然后安装`Mapstruct`插件：

![](images/7.png)

##### 4.1.2.3、添加 convert 接口

完成相关配置后，在`weblog-module-admin`模块下新建一个`convert`包，在该包下创建一个`BlogSettingsConvert` 转换接口备用：

```java
package com.cm.weblog.admin.convert;

import com.cm.weblog.admin.model.vo.blogSettings.FindBlogSettingsRspVO;
import com.cm.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    // 初始化转换器实例
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    FindBlogSettingsRspVO convertVO2DO(BlogSettingsDO blogSettingsDO);
}

```

#### 4.1.3、创建 Controller、Service、DO&Mapper

在`weblog-module-admin`中添加`AdminBlogSettingsController`控制器：

```java
package com.cm.weblog.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 分类模块")
public class AdminBlogSettingsController {
}

```

在`service`包中创建`AdminBlogSettingsService`接口：

```java
package com.cm.weblog.admin.service;

import com.cm.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
}

```

在`weblog-module-common`模块中的`domain/dos`包下创建`BlogSettingsDO`实体类：

```java
package com.cm.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_blog_settings")
public class BlogSettingsDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String logo;

    private String name;

    private String author;

    private String introduction;

    private String avatar;

    private String githubHomepage;

    private String csdnHomepage;

    private String giteeHomepage;

    private String zhihuHomepage;
}

```

在`domain/mapper`包下创建`BlogSettingsMapper`接口：

```java
package com.cm.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.weblog.common.domain.dos.BlogSettingsDO;

public interface BlogSettingsMapper extends BaseMapper<BlogSettingsDO> {
}

```

#### 4.1.4、向控制器中添加接口

修改`AdminBlogSettingsController`，添加查询博客设置详情的接口及`Service`：

```java
@RestController
@RequestMapping("/admin/blog/settings")
@Api(tags = "Admin 分类模块")
public class AdminBlogSettingsController {
    @Resource
    private AdminBlogSettingsService adminBlogSettingsService;

    @PostMapping("/detail")
    @ApiOperation(value = "获取博客设置详情")
    @ApiOperationLog(description = "获取博客设置详情")
    public Response<?> findBlogSettingDetail() {
        return adminBlogSettingsService.findBlogDetail();
    }
}
```

#### 4.1.5、完善服务层

向`AdminBlogSettingsService`服务中添加方法签名：

```java
package com.cm.weblog.admin.service;

import com.cm.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
    /**
     * 获取博客设置详情
     * @return 请求响应
     */
    Response<?> findBlogDetail();
}

```

