# 登录模块开发

## 一、登录页面开发

### 1.1、基本布局

修改`/pages/admin/Login.vue`的内容，实现一个grid 网格布局的基本骨架：

```vue
<template>
  <!-- 使用 grid 网格布局，并指定列数为 2，高度占满全屏 -->
  <div class="grid grid-cols-2 h-screen">
    <!-- 默认先适配移动端，占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端）；背景色为黑色 -->
    <div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-black">
      左边栏
    </div>
    <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
      右边栏
    </div>
  </div>
</template>
```

效果如下：

![](images/0.png)

### 1.2、右边栏登录表单

安装图标依赖：

```cmd
pnpm i @element-plus/icons-vue
```

在`src/utils`下新建注册图标的工具类`iconsIntsaller.js`：

```js
// 导入 Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 引入图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
```

在`main.js`中引入并注册：

```js
import { createApp } from 'vue'
import { installIcons } from "@/utils/iconsInstaller"

import router from "./router/index"
import App from './App.vue'

import "@/assets/styles/main.css"

const app = createApp(App)

installIcons(app)
app.use(router)
app.mount('#app')
```

开发登录表单：

```vue
<script setup>
import { User, Lock } from "@element-plus/icons-vue"
</script>

<template>
  <div class="grid grid-cols-2 h-screen">
    <div class="col-span-2 order-2 md:col-span-1 md:order-1 bg-black">
      <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
      <div class="flex justify-center items-center h-full">
        左边栏
      </div>
    </div>
    <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
      <div class="flex justify-center items-center h-full flex-col">
        <!-- 大标题，设置字体粗细、大小、下边距 -->
        <h1 class="font-bold text-4xl mb-5">欢迎回来</h1>
        <!-- 设置 flex 布局，内容垂直水平居中，文字颜色，以及子内容水平方向 x 轴间距 -->
        <div class="flex items-center justify-center mb-7 text-gray-400 space-x-2">
          <!-- 左边横线，高度为 1px, 宽度为 16，背景色设置 -->
          <span class="h-[1px] w-16 bg-gray-200"></span>
          <span>账号密码登录</span>
          <!-- 右边横线 -->
          <span class="h-[1px] w-16 bg-gray-200"></span>
        </div>
        <!-- 引入 Element Plus 表单组件，移动端设置宽度为 5/6，PC 端设置为 2/5 -->
        <el-form class="w-5/6 md:w-2/5">
          <el-form-item>
            <!-- 输入框组件 -->
            <el-input size="large" placeholder="请输入用户名" :prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item>
            <!-- 密码框组件 -->
            <el-input size="large" type="password" placeholder="请输入密码" :prefix-icon="Lock" clearable/>
          </el-form-item>
          <el-form-item>
            <!-- 登录按钮，宽度设置为 100% -->
            <el-button class="w-full" size="large" type="primary">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
```

在`main.css`中解决样式冲突问题：
```css
[type='text']:focus, [type='email']:focus, [type='url']:focus, [type='password']:focus, [type='number']:focus, [type='date']:focus, [type='datetime-local']:focus, [type='month']:focus, [type='search']:focus, [type='tel']:focus, [type='time']:focus, [type='week']:focus, [multiple]:focus, textarea:focus, select:focus {
  box-shadow: 0 0 0 1px transparent inset!important;
}
```

最终效果如下：

![](images/1.png)

### 1.3、左边栏效果开发

```html
<!-- 默认占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端） -->
<div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-slate-900">
  <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
  <div class="flex justify-center items-center h-full flex-col">
    <h2 class="font-bold text-4xl mb-7 text-white">Weblog 博客登录</h2>
    <p class="text-white">一款由 Spring Boot + Mybaits Plus + Vue 3.2 + Vite 4 开发的前后端分离博客。</p>
    <!-- 指定图片宽度为父级元素的 1/2 -->
    <img src="@/assets/images/developer.png" class="w-1/2" alt="developer" />
  </div>
</div>
```

最终效果如下：

![](images/2.png)

### 1.4、添加动画效果

安装依赖：

```cmd
pnpm install animate.css
```

在 main.js 文件中引入它：

```js
import "animate.css"
```

给右边栏的父级 div 添加 bounceInRight 动画：

```html
<div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
  <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInRight animate__fast">
    <!-- 省略 -->
  </div>
</div>
```

给左边栏的父级 div 添加 bounceInLeft 动画：

```html
<div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-slate-900">
  <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
  <div class="flex justify-center items-center h-full flex-col animate__animateanimate__bounceInLeft animate__fast">
    <!-- 省略 -->
  </div>
</div>
```

## 二、整合 MybatisPlus

### 2.1、连接数据库新建数据表

命令行运行：

```cmd
net start mysql90
```

> 关闭数据库的命令：
>
> ```cmd
> net stop mysql90
> ```

打开`Navicat`连接数据库：

![](images/3.png)

![](images/4.png)

新建一个数据库：

![](images/5.png)

新建查询语句，执行如下的建表语句：

```sql
CREATE TABLE `t_user` (
	`id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`username` VARCHAR(60) NOT NULL COMMENT '用户名',
	`password` VARCHAR(60) NOT NULL COMMENT '密码',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	`is_deleted` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0：未删除；1：已删除',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

### 2.2、添加依赖

在`weblog-springbbot`的`pom.xml`文件中声明版本及依赖：

```xml
<properties>
	<!-- 省略 -->
    <mybatis-plus.version>3.5.2</mybatis-plus.version>
</properties>

<dependencies>
	<!-- 省略 -->
    <!-- Mybatis Plus -->
    <dependency>
    	<groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>${mybatis-plus.version}</version>
    </dependency>
</dependencies>
```

在`weblog-module-common`模块中引入`MybatisPlus`以及`MySQL`依赖：

```xml
<dependencies>
	<!-- 省略 -->
    <!-- Mybatis Plus -->
    <dependency>
    	<groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
    </dependency>
    
    <!-- MySQL -->
    <dependency>
    	<groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
</dependencies>
```

### 2.3、添加配置

编辑`application-dev.yml`，添加数据库连接及连接池相关配置：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/weblog?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull
    username: root
    password: admin123456
    hikari:
      minimum-idle: 5 # 最小空闲连接数
      maximum-pool-size: 20 # 连接池最大允许连接数
      auto-commit: true # 自动提交事务
      idle-timeout: 30000 # 连接闲置最长时间（超过这个时间会被释放）
      pool-name: Weblog-HikariCP # 连接池命名
      max-lifetime: 1800000 # 连接在连接池最大存活时间（超过这个时间会被强制关闭）
      connection-timeout: 30000 # 连接超时时间
      connection-test-query: SELECT 1 # 测试连接是否可用
```

在`weblog-module-common`模块中的`config`包下，新建一个`MybatisPlusConfig`配置类：

```java
package com.cm.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.cm.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}

```

- `@MpperScan`：指定要扫描的位置，即`mapper`接口存放的位置（数据库相关的代码统一放置在`/domain`包下），如下图所示：

  ![](images/6.png)

  - `dos`：根据阿里开发规划，数据库对应实体类统一存放此包下
  - `mapper`：统一放置`mapper`接口文件

### 2.4、尝试新增一条用户记录

在`/dos`包下，新建一个`UserDO`类与数据库中的字段对应：

```java
package com.cm.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_user")
public class UserDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    private String password;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Boolean isDeleted;
}

```

新建`Mapper`接口——在`mapper`包中，创建一个`UserMapper`接口，代码如下：

```java
package com.cm.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.weblog.common.domain.dos.UserDO;

public interface UserMapper extends BaseMapper<UserDO> {
}

```

在`weblog-web`模块的单侧中新增一个测试方法，往数据库新增一条用户记录：

```java
@Autowired
private UserMapper userMapper;
    
@Test
void insertTest() {
	// 构建数据库实体类
	UserDO userDO = UserDO.builder()
			.username("admin")
			.password("123456")
			.createTime(new Date())
			.updateTime(new Date())
			.isDeleted(false)
			.build();
        
	userMapper.insert(userDO);
}
```

运行测试方法，查看数据库：

![](images/7.png)

## 三、整合 P6spy 组件

### 3.1、添加依赖

在`weblog-springboot`模块中声明依赖以及版本号：

```xml
<properties>
	<!-- 省略 -->
    <p6spy.version>3.9.1</p6spy.version>
</properties>

<dependencies>
	<!-- 省略 -->
    <dependency>
    	<groupId>p6spy</groupId>
        <artifactId>p6spy</artifactId>
        <version>${p6spy.version}</version>
    </dependency>
</dependencies>
```

在`weblog-module-common`模块中引入依赖：

```xml
<dependency>
	<groupId>p6spy</groupId>
	<artifactId>p6spy</artifactId>
</dependency>
```

### 3.2、添加配置

修改`application-dev.yml`，将驱动类修改为`p6spy`提供的驱动类；将数据库地址前缀修改为`jdbc:p6spy`：

```yml
spring:
	datasource:
		driver-class-name: com.p6spy.engine.spy.P6SpyDriver
		url: jdbc:p6spy:mysql://127.0.0.1:3306/weblog?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull
		# ... 省略
```

在`weblog-web`模块的`resources`目录下添加`spy.properties`文件，内容如下：

```properties
modulelist=com.baomidou.mybatisplus.extension.p6spy.MybatisPlusLogFactory,com.p6spy.engine.outage.P6OutageFactory
# 自定义打印日志
logMessageFormat=com.baomidou.mybatisplus.extension.p6spy.P6SpyLogger
# 输出日志到控制台
appender=com.baomidou.mybatisplus.extension.p6spy.StdoutLogger
# 设置 p6spy driver 代理
deregisterdrivers=true
# JDBC 前缀
usePrefix=true
# 配置可去掉的结果集
excludecategories=info,debug,result,commit,resultset
# 日期格式
dateformat=yyyy-MM-dd HH:mm:ss
# 开启慢 SQL 记录
outagedetection=true
# 慢 SQL 记录标准 2 秒
outagedetactioninterval=2
```

### 3.3、测试打印完整的 SQL 语句以及耗时

删除`t_user`表中数据，再次运行上节中的`insertTest`方法，查看效果：

![](images/8.png)

### 备注：生产环境不要启用

即`application-prod.yml`中数据库连接的配置仍然使用原来的：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/weblog?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull
    username: root
    password: admin123456
    hikari:
      minimum-idle: 5 # 最小空闲连接数
      maximum-pool-size: 20 # 连接池最大允许连接数
      auto-commit: true # 自动提交事务
      idle-timeout: 30000 # 连接闲置最长时间（超过这个时间会被释放）
      pool-name: Weblog-HikariCP # 连接池命名
      max-lifetime: 1800000 # 连接在连接池最大存活时间（超过这个时间会被强制关闭）
      connection-timeout: 30000 # 连接超时时间
      connection-test-query: SELECT 1 # 测试连接是否可用
      
# 日志
logging:
  config: classpath:logback-weblog.xml
```

