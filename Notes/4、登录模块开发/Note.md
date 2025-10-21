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

## 四、整合 Spring Security

### 4.1、新建 weblog-module-jwt 模块

参考之前创建模块的方式，创建一个`weblog-module-jwt`模块，放置`jwt`相关功能代码

创建成功后，删除一些无用的文件、文件夹，最终目录如下：

![](images/9.png)

修改`pom.xml`文件内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.cm</groupId>
        <artifactId>weblog-springboot</artifactId>
        <version>${revision}</version>
    </parent>

    <artifactId>weblog-module-jwt</artifactId>
    <name>weblog-module-jwt</name>
    <description>weblog-module-jwt（JWT 模块：管理用户认证、鉴权）</description>

    <dependencies>
       <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>

```

在父项目`weblog-springboot`中引入模块

```xml
<modules>
	<!-- 省略 -->
    <module>weblog-module-jwt</module>
</modules>

<dependencies>
    <!-- 省略 -->
	<dependency>
    	<groupId>com.cm</groupId>
        <artifactId>weblog-module-jwt</artifactId>
        <version>${revision}</version>
    </dependency>
</dependencies>
```

在`weblog-module-admin`模块中引入——认证、鉴权功能只在`Admin`后台中需要：

```xml
<!-- 省略 -->
<dependency>
	<groupId>com.cm</groupId>
	<artifactId>weblog-module-jwt</artifactId>
</dependency>
```

### 4.2、添加依赖

在`weblog-module-admin`和`weblog-module-jwt`模块中添加`security`依赖：

```xml
<!-- 省略 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 4.3、自定义 Security 配置

在`weblog-module-admin`模块的`config`包下新建一个`WebSecurityConfig`配置类：

```java
package com.cm.weblog.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() // 所有以 /admin 开头的接口需要认证
                .anyRequest().permitAll() // 其它接口放行，无需认证
                .and()
                .formLogin() // 使用表单登录
                .and()
                .httpBasic(); // 使用 HTTP Basic 认证
    }
}

```

### 4.4、测试

在`application-dev.yml`文件中自定义测试用的登录用户名和密码：

```yml
spring:
	# 省略...
	security:
		user:
			name: admin
			password: 123456
```

将之前的`/test`接口修改为`/admin/test`接口，重启项目，访问 http://localhost:8080/admin/test，结果如下：

![](images/10.png)

接口被拦截了兵跳转到了`security`包默认的登录页，输入之前设置的用户名、密码后，能够正常访问接口：

![](images/11.png)

> 虽然接口返回错误信息，但这是因为以`GET`访问了`POST`接口，与本节的需求没有关系。从测试结果来说，接口被正常拦截在登录后也能被正常访问，测试成功。

## 五、Spring Security 整合 JWT 实现身份认证

### 5.1、添加 JWT 依赖

在`weblog-springboot`模块中添加版本号及相关依赖：

```xml
<properties>
	<!-- 省略 -->
    <jjwt.version>0.11.2</jjwt.version>
</properties>

<dependencies>
	<!-- 省略 -->
    <dependency>
    	<groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>${jjwt.version}</version>
    </dependency>
    
    <dependency>
    	<groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>${jjwt.version}</version>
    </dependency>
    
    <dependency>
    	<groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>${jjwt.version}</version>
    </dependency>
</dependencies>
```

在`weblog-module-jwt`模块中引入`jwt`相关依赖、`common-lang3`依赖、`spring-boot-starter-web`依赖、`jackson`依赖以及`weblog-module-common`模块

```xml
<!-- 省略 -->
<!-- JWT -->
<dependency>
	<groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
</dependency>

<dependency>
	<groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
</dependency>

<dependency>
	<groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
	<groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>

<dependency>
	<groupId>com.cm</groupId>
    <artifactId>weblog-module-common</artifactId>
</dependency>

<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
</dependency>
```

### 5.2、编写 JWT 工具类

在`weblog-module-jwt`模块下新建`utils`包，在包中创建一个`JwtTokenHelper`工具类，封装`JWT`相关功能：

```java
package com.cm.weblog.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * 封装 JWT 相关的功能
 */
@Component
public class JwtTokenHelper implements InitializingBean {
    // 签发人
    @Value("${jwt.issuer")
    private String issuer;

    // 秘钥
    private Key key;

    // 解析器
    private JwtParser jwtParser;

    /**
     * 解码 application.yml 配置文件中的 secret 字段 为秘钥
     * @param base64Key secret 的base64编码
     */
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key) {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    /**
     * 初始化解析器
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }

    /**
     * 编译 Token
     * @param username 用户名
     * @return 根据用户名加密生成的 Token 且一小时后失效
     */
    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(1);

        return Jwts.builder().setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact();
    }

    /**
     * 解析 Token
     * @param token Token
     * @return 解码 Token 中的信息
     */
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 生成一个 Base64 编码的安全秘钥
     * @return 安全秘钥
     */
    private static String generateBase64Key() {
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println("key: " + key);
    }
}

```

执行该工具类的`main`方法，将生成好的秘钥配置到`application.yml`中：

```yml
spring:
  profiles:
    active: '@env@'
    
jwt:
  # 签发人
  issuer: CM
  # 秘钥
  secret: JUjN5GvIe/mc04kQA7I4Iy5CtroT5zUsYM29Iyu3RwYcpdh/ZcaYcJBDHrUuQINcyxuOiKX3prlNmuc7Y0868g==
```

### 5.3、加密密码

在`weblog-module-jwt`模块下新建`config`包，在包中创建一个`PasswordEncoderConfig`配置类：

```java
package com.cm.weblog.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密配置类
 */
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("admin123456"));
    }
}

```

> `PasswordEcoder`接口是`Spring Security`提供的密码加密接口，它定义了密码加密和密码验证方法，通过实现这个接口可以将密码加密为不可逆的哈希值，以及在验证密码时对比

### 5.4、用户详情服务

调用上节`PasswordEncoderConfig`中的`main`方法，生成一个密码密文，在实现类中使用。

在`weblog-module-jwt`模块下新建`service`包，并创建`UserDetailServiceImpl`实现类：

```java
package com.cm.weblog.jwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情服务实现类
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 暂时先写死
        return User.withUsername("admin")
                .password("$2a$10$nL1x8aqM.Lfm..AYXiFVFeBBUU.Vjinc9NCSqoRrnw7E.F9s10Mxe")
                .authorities("ADMIN")
                .build();
    }
}

```

> `UserDatilService`是`Spring Security`提供的接口，用于从应用程序的数据源（数据库、LDAP、内存等）中加载用户信息
>
> 后续会去数据库中加载用户信息，暂时先写死

### 5.5、自定义认证过滤器

新建`exception`包，自定义一个用户名或密码不能为空异常`UsernameOrPasswordNullException`：

```java
package com.cm.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义用户名或密码为空异常
 */
public class UsernameOrPasswordNullException extends AuthenticationException {
    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }
    
    public UsernameOrPasswordNullException(String msg, Throwable t) {
        super(msg, t);
    }
}

```

新建`filter`包，创建`JwtAuthenticationFilter`过滤器处理用户身份认证过程，当请求路径为`/login`且方法为`POST`时触发该过滤器，解析收到的用户名、密码并把它们封装到`UsernamePasswordAuthenticationToken`中，返回身份验证结果，代码如下：

```java
package com.cm.weblog.jwt.filter;

import com.cm.weblog.jwt.exception.UsernameOrPasswordNullException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义用户认证过滤器
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST")); // 指定要过滤的请求
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(request.getInputStream());
        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode = jsonNode.get("password");

        if (Objects.isNull(usernameNode) || Objects.isNull(passwordNode) || StringUtils.isBlank(usernameNode.textValue()) || StringUtils.isBlank(passwordNode.textValue())) {
            throw new UsernameOrPasswordNullException("用户名或密码不能为空");
        }

        String username = usernameNode.textValue();
        String password = passwordNode.textValue();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(authRequest); // 触发 Spring Security 的身份验证管理器执行实际的身份验证过程返回身份验证结果
    }
}

```

### 5.6、认证成功处理

新建`model`包，创建登录后的响应参数类`LoginRspVO`：

```java
package com.cm.weblog.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应参数实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRspVO {
    private String token;
}

```

在`utils`包下，封装一个`ResultUtil`类用于在处理器中返回`JSON`参数：

```java
package com.cm.weblog.jwt.utils;

import com.cm.weblog.common.utils.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理返回参数
 */
public class ResultUtil {
    /**
     * 成功时响应参数
     * @param response 请求响应
     * @param result 请求结果
     * @throws IOException 异常
     */
    public static void ok(HttpServletResponse response, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        
        PrintWriter writer = response.getWriter();
        
        ObjectMapper mapper = new ObjectMapper();
        
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败时响应参数
     * @param response 请求响应
     * @param result 请求结果
     * @throws IOException 异常
     */
    public static void fail(HttpServletResponse response, Response<?> result) throws IOException {
        ok(response, result);
    }

    /**
     * 失败时响应参数
     * @param response 请求响应
     * @param status 响应状态
     * @param result 请求结果
     * @throws IOException 异常
     */
    public static void fail(HttpServletResponse response, int status, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();

        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}

```

新建`handler`包，创建`RestAuthenticationSuccessHandler`认证成功处理器：

```java
package com.cm.weblog.jwt.handler;

import com.cm.weblog.common.utils.Response;
import com.cm.weblog.jwt.model.LoginRspVO;
import com.cm.weblog.jwt.utils.JwtTokenHelper;
import com.cm.weblog.jwt.utils.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 */
@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtTokenHelper jwtTokenHelper;
    

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取用户详情服务返回的用户实例
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // 通过用户名生成 Token
        String username = userDetails.getUsername();
        String token = jwtTokenHelper.generateToken(username);

        LoginRspVO loginRspVO = LoginRspVO.builder().token(token).build();

        ResultUtil.ok(response, Response.success(loginRspVO));
    }
}
```

> 从`authentication`对象中获取`UserDetails`实例中的用户名，通过用户名生成`Token`令牌

### 5.7、认证失败处理

在`weblog-module-common`模块的`ResponseCodeEnum`中添加登录失败的响应码：

```java
LOGIN_FAIL("20000", "登录失败"),
USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误")
```

在`handler`包下，创建`RestAuthenticationFailureHandler`认证失败处理器：

```java
package com.cm.weblog.jwt.handler;

import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.utils.Response;
import com.cm.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component
@Slf4j
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
      log.warn("AuthenticationException: ", exception);
      
      if (exception instanceof UsernameOrPasswordNullException) {
          ResultUtil.fail(response, Response.fail(exception.getMessage()));
          return;
      } else if (exception instanceof BadCredentialsException) {
          ResultUtil.fail(response, Response.fail(ResponseCodeEnum.USERNAME_OR_PWD_ERROR));
          return;
      }
      
      ResultUtil.fail(response, Response.fail(ResponseCodeEnum.LOGIN_FAIL));
    }
}

```

> 首先打印异常日志，然后判断异常通过`ResultUitl`工具类返回不同参数信息，若为判断出异常类型则统一提示登录失败

### 5.8、JWT 认证功能配置

完成上述前置工作后，还需一个`Spring Security`配置类整合这些过滤器、处理器、加密算法等

在`config`包下创建`JwtAuthenticationSecurityConfig`配置类：

```java
package com.cm.weblog.jwt.config;

import com.cm.weblog.jwt.filter.JwtAuthenticationFilter;
import com.cm.weblog.jwt.handler.RestAuthenticationFailureHandler;
import com.cm.weblog.jwt.handler.RestAuthenticationSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Jwt 认证配置类
 */
@Configuration
public class JwtAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Resource
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    
    @Resource
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // 自定义用于 JWT 身份验证的过滤器
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        filter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));
        
        // 设置对应的处理类
        filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
        
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        httpSecurity.authenticationProvider(provider);
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}

```

修改`weblog-module-admin`中`WebSecurity`类的内容，引入`JWT`配置：

```java
package com.cm.weblog.admin.config;

import com.cm.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 csrf
                .formLogin().disable()// 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() // 所有以 /admin 开头的接口需要认证
                .anyRequest().permitAll() // 其它接口放行，无需认证
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

```

### 5.9、测试

重启项目，请求`/login`

**测试用户名密码为空的情况**

入参：

```json
{
    "username": "",
    "password": ""
}
```

返回：

```json
{
  "success": false,
  "message": "用户名或密码不能为空",
  "code": null,
  "data": null
}
```

**测试用户名密码错误的情况**

入参：

```json
{
    "username": "admin",
    "password": "aaa"
}
```

返回：

```json
{
  "success": false,
  "message": "用户名或密码错误",
  "code": "20001",
  "data": null
}
```

**测试用户名密码正确**

入参：

```json
{
    "username": "admin",
    "password": "admin123456"
}
```

返回：

```json
{
  "success": true,
  "message": null,
  "code": null,
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6IiR7and0Lmlzc3VlciIsImlhdCI6MTc1OTY3MzEwNiwiZXhwIjoxNzU5Njc2NzA2fQ.HKvQoTsrXKRXWQK0dZd_sZJbPjP_GkwK6rv-vX1wIirwFy2luK_44Huc8yZBGWc82xJ_ez5NHyZXykvjBojOtg"
  }
}
```

### 5.10、改为从数据库中查询用户信息

首先将数据表`t_user`中的数据删除干净，并执行如下语句，添加一条用户名"admin"，密码为"admin123456"（先在`PasswordEncoderConifg`中获取密文）的数据

```sql
INSERT INTO `weblog`.`t_user` (`username`, `password`, `create_time`, `update_time`, `is_deleted`) VALUES('admin', '$2a$10$nL1x8aqM.Lfm..AYXiFVFeBBUU.Vjinc9NCSqoRrnw7E.F9s10Mxe', now(), now(), 0);
```

然后编辑`UserMapper`接口，添加一个根据用户名查询信息的默认方法：

```java
package com.cm.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.weblog.common.domain.dos.UserDO;

public interface UserMapper extends BaseMapper<UserDO> {
    default UserDO findByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        return selectOne(wrapper);
    }
}

```

最后，编辑`UserDetailServiceImpl`类，改为从数据库中查询：

```java
package com.cm.weblog.jwt.service;

import com.cm.weblog.common.domain.dos.UserDO;
import com.cm.weblog.common.domain.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户详情服务实现类
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 暂时先写死
        /*return User.withUsername("admin")
                .password("$2a$10$nL1x8aqM.Lfm..AYXiFVFeBBUU.Vjinc9NCSqoRrnw7E.F9s10Mxe")
                .authorities("ADMIN")
                .build();*/

        // 改为从数据库中查询
        UserDO userDO = userMapper.findByUsername(username);

        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities("ADMIN") // 暂时先写死为 ADMIN
                .build();
    }
}

```

重启项目，再次测试登录接口

## 六、Spring Security 整合 JWT 实现接口鉴权

### 6.1、新增校验失败的处理器

首先在`ResponseCodeEnum`中添加枚举：

```java
UNAUTHORIZED("20002", "无访问权限，请先登录！")
```

在`handler`包中新增`RestAuthenticationEntryPoint`处理器，处理用户未登录却在访问受保护资源的情况：

```java
package com.cm.weblog.jwt.handler;

import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.utils.Response;
import com.cm.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录处理器
 */
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("用户未登录时访问受保护的资源：", authException);
        
        if (authException instanceof InsufficientAuthenticationException) {
            ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(ResponseCodeEnum.UNAUTHORIZED));
            return;
        }
        
        ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(authException.getMessage()));
    }
}

```

以及新增`RestAccessDeniedHandler`处理器，处理用户已登录后访问受保护的资源但权限不够的情况：

```java
package com.cm.weblog.jwt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户权限不足处理器
 */
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("该用户暂无权限：", accessDeniedException);

        /*
        * 预留，目前只有 ADMIN 角色，后续有更多角色时处理
        * */
    }
}

```

### 6.2、新建 Token 校验过滤器

在`JwtTokenHelper`类中添加两个方法：

- 校验`Token`
- 解析`Token`

```java
/**
* 校验 Token 是否可用
* @param token Token
*/
public void validateToken(String token) {
	jwtParser.parseClaimsJws(token);
}

/**
* 根据 Token 获取用户名
* @param token Token
* @return 用户名
*/
public String getUsernameFromToken(String token) {
	Claims claims = jwtParser.parseClaimsJws(token).getBody();
	return claims.getSubject();
}
```

在`weblog-module-jwt`模块下的`filter`包中，创建`TokenAuthenticationFilter`过滤器专门校验`Token`，代码如下：

1. 从请求头中获取`Authorization`的值
2. 是否以`Bearer`开头
3. 截取`Token`
4. 判空`Token`以及`Token`是否可用
5. 从`Token`解析用户名并获取用户详情存入`UsernamePasswordAuthenticationToken`方便后续鉴权
6. 将`UsernamePasswordAuthenticationToken`存入`ThreadLocal`方便获取用户信息
7. 继续执行下一个过滤器

```java
package com.cm.weblog.jwt.filter;

import com.cm.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token 校验过滤器
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenHelper jwtTokenHelper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 从请求头获取 key 为 Authorization 的值
        String header = request.getHeader("Authorization");

        // 判断是否以 Bearer 开头
        if (StringUtils.startsWith(header, "Bearer")) {
            // 截取 Token
            String token = StringUtils.substring(header, 7);
            log.info("token: {}", token);

            // 判空
            if (StringUtils.isNotBlank(token)) {
                try {
                    jwtTokenHelper.validateToken(token);
                } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                    return;
                } catch (ExpiredJwtException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                    return;
                }

                String username = jwtTokenHelper.getUsernameFromToken(token);

                if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}

```

### 6.3、整合配置

将上述新增的过滤器与处理器整合到`Spring Security`的配置类`WebSecurityConfig`中：

```java
package com.cm.weblog.admin.config;

import com.cm.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import com.cm.weblog.jwt.filter.TokenAuthenticationFilter;
import com.cm.weblog.jwt.handler.RestAccessDeniedHandler;
import com.cm.weblog.jwt.handler.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    
    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 csrf
                .formLogin().disable()// 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关配置
              .and()
                .authorizeRequests()
                .mvcMatchers("/admin/**").authenticated() // 所有以 /admin 开头的接口需要认证
                .anyRequest().permitAll() // 其它接口放行，无需认证
              .and()
                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)
              .and()
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
              .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}

```

提取相关变量到配置文件中

修改`application.yml`配置文件如下：

```yml
spring:
  profiles:
    active: '@env@'

jwt:
  # 签发人
  issuer: CM
  # 秘钥
  secret: JUjN5GvIe/mc04kQA7I4Iy5CtroT5zUsYM29Iyu3RwYcpdh/ZcaYcJBDHrUuQINcyxuOiKX3prlNmuc7Y0868g==
  # Token 过期时间（分钟）
  tokenExpireTime: 1440
  # Token 请求头 key 值
  tokenHeaderKey: Authorization
  # Token 值前缀
  tokenPrefix: Bearer
```

修改`JwtTokenHelper`类如下：

```java
package com.cm.weblog.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * 封装 JWT 相关的功能
 */
@Component
public class JwtTokenHelper implements InitializingBean {
    // 签发人
    @Value("${jwt.issuer}")
    private String issuer;

    // 秘钥
    private Key key;

    // 解析器
    private JwtParser jwtParser;
    
    // 过期时间
    @Value("${jwt.tokenExpireTime}")
    private Long tokenExpireTime;

    /**
     * 解码 application.yml 配置文件中的 secret 字段 为秘钥
     * @param base64Key secret 的base64编码
     */
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key) {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    /**
     * 初始化解析器
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }

    /**
     * 编译 Token
     * @param username 用户名
     * @return 根据用户名加密生成的 Token 且一小时后失效
     */
    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusMinutes(tokenExpireTime);

        return Jwts.builder().setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact();
    }

    /**
     * 解析 Token
     * @param token Token
     * @return 解码 Token 中的信息
     */
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 校验 Token 是否可用
     * @param token Token
     */
    public void validateToken(String token) {
        jwtParser.parseClaimsJws(token);
    }

    /**
     * 根据 Token 获取用户名
     * @param token Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * 生成一个 Base64 编码的安全秘钥
     * @return 安全秘钥
     */
    private static String generateBase64Key() {
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println("key: " + key);
    }
}

```

修改`TokenAuthenticationFilter`的内容如下：

```java
package com.cm.weblog.jwt.filter;

import com.cm.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token 校验过滤器
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenHelper jwtTokenHelper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;
    
    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 从请求头获取 key 为 Authorization 的值
        String header = request.getHeader(tokenHeaderKey);

        // 判断是否以 Bearer 开头
        if (StringUtils.startsWith(header, tokenPrefix)) {
            // 截取 Token
            String token = StringUtils.substring(header, 7);
            log.info("token: {}", token);

            // 判空
            if (StringUtils.isNotBlank(token)) {
                try {
                    jwtTokenHelper.validateToken(token);
                } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                    return;
                } catch (ExpiredJwtException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                    return;
                }

                String username = jwtTokenHelper.getUsernameFromToken(token);

                if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}

```

### 6.4、测试

重启项目，调用登录接口，获取一个新的`Token`

在请求调试工具中设置全局请求头参数

请求`/admin/test`接口，结果如下：

入参：

```json
{
    "username": "宰晨",
    "sex": 0,
    "age": 76,
    "email": "ixjit6.ik1@163.com"
}
```

返回：

```json
{
    "success": true,
    "message": null,
    "code": null,
    "data": {
        "username": "宰晨",
        "sex": 0,
        "age": 76,
        "email": "ixjit6.ik1@163.com",
        "createTime": "2025-10-08 21:40:12",
        "updateDate": "2025-10-08",
        "time": "21:40:12"
    }
}
```

故意将`Token`改成错误的，结果如下：

```json
{
    "success": false,
    "message": "Token 不可用",
    "code": null,
    "data": null
}
```

## 七、前端请求 /login 接口以及配置跨域

### 7.1、安装Axios

```shell
pnpm i axios
```

### 7.2、创建 Axios 实例

在`utils`目录下创建文件`axios.js`文件，配置内容如下：

```js
import axios from "axios"

// 创建 Axios 实例
const instance = axios.create({
  baseURL: "/api", // 你的 API 基础 URL
  timeout: 7000, // 请求超时时间
})

// 暴露出去
export default instance
```

### 7.3、封装请求

在`src`目录下创建`api`文件夹，在该文件夹下新建两个文件夹：

- `admin`：管理后台相关接口
- `frontend`：管理前台相关接口

在`admin`文件夹下创建`user.js`，统一放置用户相关接口，目前只有登录，内容如下：

```js
import axios from "@/utils/axios"

// 登录接口
export function login(username, password) {
  return axios.post("/login", {username, password})
}
```

### 7.4、配置代理发送请求

在`vite.config.js`配置代理解决跨域问题：

```js
export default defineConfig({
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "")
      },
    }
  },
	// 省略...
})
```

给登录页的表单绑定响应式对象，点击登录按钮发送登录请求：

```vue
<script setup>
import { User, Lock } from "@element-plus/icons-vue"
import { reactive } from "vue"
import { login } from "@/api/admin/user"

const loginForm = reactive({
  username: "",
  password: ""
})

async function onsubmit() {
  try {
    const { data } = await login(loginForm)
    console.log(data)
  } catch(e) {
    console.log(e)
  }
}
</script>

<template>
  <div class="grid grid-cols-2 h-screen">
    <!-- 默认占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端） -->
    <div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-slate-900">
      <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
      <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInLeft animate__fast">
        <h2 class="font-bold text-4xl mb-7 text-white">Weblog 博客登录</h2>
        <p class="text-white">一款由 Spring Boot + Mybaits Plus + Vue 3.2 + Vite 4 开发的前后端分离博客。</p>
        <!-- 指定图片宽度为父级元素的 1/2 -->
        <img src="@/assets/images/developer.png" class="w-1/2" alt="developer" />
      </div>
    </div>
    <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
      <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInRight animate__fast">
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
            <el-input v-model="loginForm.username" size="large" placeholder="请输入用户名" :prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item>
            <!-- 密码框组件 -->
            <el-input v-model="loginForm.password" size="large" type="password" placeholder="请输入密码" :prefix-icon="Lock" clearable/>
          </el-form-item>
          <el-form-item>
            <!-- 登录按钮，宽度设置为 100% -->
            <el-button class="w-full" size="large" type="primary" @click="onsubmit">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
```

### 7.5、登录后跳转

在`admin`目录下创建`index.vue`，表示登录后的首页，目前先不添加内容，后续再完善：

```vue
<template>
    <div>
        后台首页
    </div>
</template>
```

在路由中添加：

```js
import AdminIndex from "@/pages/admin/index.vue"

const routes = [
    // 省略...
    {
        path: "/admin/index", // 后台首页
        component: AdminIndex,
        meta: {
            title: "Admin 后台首页"
        }
    }
]
```

登录成功后通过`router`跳转到首页：

```vue
<script setup>
// 省略...
import { useRouter } from "vue-router"

const router = useRouter()

// 登录
const onSubmit = () => {
    login(form.username, form.password).then((res) => {
        console.log(res)
        // 判断是否成功
        if (res.data.success == true) {
            // 跳转到后台首页
            router.push("/admin/index")
        }
    })
}

</script>
```

## 八、登录页添加表单验证

修改`Login.vue`，给`ElementPlus`表单组件提供`rules`，给需要校验的表单项添加`props`，在调用登录接口之前先校验表单：

```vue
<script setup>
import { User, Lock } from "@element-plus/icons-vue"
import { reactive, ref } from "vue"
import { login } from "@/api/admin/user"
import { useRouter } from "vue-router"

const router = useRouter()

const loginForm = reactive({
  username: "",
  password: ""
})
const rules = {
  username: [
    {
      required: true,
      message: "用户名不能为空",
      trigger: "blur"
    }
  ],
  password: [
    {
      required: true,
      message: "密码不能为空",
      trigger: "blur"
    }
  ]
}
const loginFormRef = ref()

async function onsubmit() {
  loginFormRef.value.validate(async valid => {
    if (valid) {
      try {
        const { data } = await login(loginForm.username, loginForm.password)
        console.log(data)
        if (data.success) {
          router.push("/admin/index")
        }
      } catch(e) {
        console.log(e)
      }
    }
  })
}
</script>

<template>
  <div class="grid grid-cols-2 h-screen">
    <!-- 默认占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端） -->
    <div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-slate-900">
      <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
      <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInLeft animate__fast">
        <h2 class="font-bold text-4xl mb-7 text-white">Weblog 博客登录</h2>
        <p class="text-white">一款由 Spring Boot + Mybaits Plus + Vue 3.2 + Vite 4 开发的前后端分离博客。</p>
        <!-- 指定图片宽度为父级元素的 1/2 -->
        <img src="@/assets/images/developer.png" class="w-1/2" alt="developer" />
      </div>
    </div>
    <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
      <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInRight animate__fast">
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
        <el-form ref="loginFormRef" class="w-5/6 md:w-2/5" :model="loginForm" :rules="rules">
          <el-form-item prop="username">
            <!-- 输入框组件 -->
            <el-input v-model="loginForm.username" size="large" placeholder="请输入用户名" :prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item prop="password">
            <!-- 密码框组件 -->
            <el-input v-model="loginForm.password" size="large" type="password" placeholder="请输入密码" :prefix-icon="Lock" clearable/>
          </el-form-item>
          <el-form-item>
            <!-- 登录按钮，宽度设置为 100% -->
            <el-button class="w-full" size="large" type="primary" @click="onsubmit">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
```

## 九、登录页面优化

### 9.1、登录消息提示

在`utils`目录下新建`message.js`文件用于封装消息提示工具，内容如下：

```js
export function showMessage(message = "提示内容", type = "success", customClass = "") {
    return ELMessage({
        type,
        message,
        customClass
    })
}
```

修改`login.vue`内容，引用上述消息工具类

```vue
<script>
import { showMessage } from "@/utils/message.js"
    
// ...省略
async function onsubmit() {
  loginFormRef.value.validate(async valid => {
    if (valid) {
      try {
        const { data } = await login(loginForm.username, loginForm.password)
        if (data.success) {
          showMessage("登录成功")
          router.push("/admin/index")
        } else {
          let message = data.message
          showMessage(message, "error")
        }
      } catch(e) {
        console.log(e)
        showMessage("登录失败", "error")
      }
    }
  })
}
</script>
```

### 9.2、回车登录

修改`login.vue`文件内容，添加回车事件：

```vue
<script>
	function handleKeyUp(e) {
  		if (e.key === "Enter") {
    		onsubmit()
  		}
	}
	onMounted(() => {
  		document.addEventListener("keydown", handleKeyUp)
	})
	onBeforeUnmount(() => {
  		document.removeEventListener("keydown", handleKeyUp)
	})
</script>
```

### 9.3、按钮加载

修改`login.vue`，在请求登录接口时按钮加载：

```vue
<script>
    // ...省略
	const loading = ref(false)
	async function onsubmit() {
  	loginFormRef.value.validate(async valid => {
    	if (valid) {
      		loading.value = true
      		try {
        		const { data } = await login(loginForm.username, loginForm.password)
        		if (data.success) {
          			showMessage("登录成功")
          			router.push("/admin/index")
        		} else {
          			let message = data.message
          			showMessage(message, "error")
        		}
      		} catch(e) {
        		console.log(e)
        		showMessage("登录失败", "error")
      		} finally {
        		loading.value = false
      		}
    	}
  	})
	}
</script>

<template>
	<!-- 省略 -->
	<el-button :loading="loading" class="w-full" size="large" type="primary" @click="onsubmit">登录</el-button>
</template>
```

## 十、存储 Token 到 Cookie 中

### 10.1、安装 依赖

```shell
pnpm i @vueuse/integrations
pnpm i universal-cookie
```

### 10.2、封装工具类

在`utils`目录下新建`cookie.js`文件封装`cookie`相关的操作：

- 获取`Token`
- 存储`Token`
- 删除`Token`

```js
import { useCookies } from "@vueuse/integrations/useCookies"

const TOKEN_KEY = "Authorization"
const cookie = useCookies()

export function getToken() {
    return cookie.get(TOKEN_KEY)
}

export function setToken(token) {
    return cookie.set(TOKEN_KEY, token)
}

export function removeToken() {
    return cookie.remove(TOKEN_KEY)
}
```

### 10.3、登录成功后存储 Token

修改`login.vue`中`onSubmit`方法，登录成功后存储`Token`到`Cookie`中：

```vue
<script setup>
// ...省略
import { setToken } from "@/utils/cookie.js"
    
async function onsubmit() {
  loginFormRef.value.validate(async valid => {
    if (valid) {
      loading.value = true
      try {
        const { data } = await login(loginForm.username, loginForm.password)
        if (data.success) {
          showMessage("登录成功")
          let token = data.data.token
          setToken(token)
          await router.push("/admin/index")
        } else {
          let message = data.message
          showMessage(message, "error")
        }
      } catch(e) {
        console.log(e)
        showMessage("登录失败", "error")
      } finally {
        loading.value = false
      }
    }
  })
}
</script>
```

登录后可以看到已存储的`Token`：

![](images/12.png)

## 十一、Axios 拦截器配置

### 11.1、请求拦截器

**给请求头添加`Token`**

修改`axios.js`文件，添加请求拦截器：

```js
// 添加请求拦截器
instance.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, error => {
  return Promise.reject(error)
})
```

### 11.2、添加响应拦截器

**优化取参不方便的问题**

修改`axios.js`文件，添加响应拦截器：

```js
// 添加响应拦截器
instance.interceptors.response.use(response => {
  return response.data
}, error => {
  return Promise.reject(error)
})
```

修改完成后，还要修改`login.vue`中接收响应参数的代码：

```js
async function onsubmit() {
  loginFormRef.value.validate(async valid => {
    if (valid) {
      loading.value = true
      try {
        const res = await login(loginForm.username, loginForm.password)
        if (res.success) {
          showMessage("登录成功")
          let token = res.data.token
          setToken(token)
          await router.push("/admin/index")
        } else {
          let message = res.message
          showMessage(message, "error")
        }
      } catch(e) {
        console.log(e)
        showMessage("登录失败", "error")
      } finally {
        loading.value = false
      }
    }
  })
}
```

**统一处理请求失败问题**

修改响应拦截器配置：

```js
instance.interceptors.response.use(response => {
  return response.data
}, error => {
  let errorMessage = error.response.data.message || "请求失败"
  showMessage(errorMessage, "error")
  return Promise.reject(error)
})
```

## 十二、全局路由拦截

### 12.1、前置路由守卫判断登录状态

在`router`目录下新建`permission.js`文件，添加路由前置守卫：

```js
import router from "."

import { getToken } from "@/utils/cookie"
import { showMessage } from "@/utils/message"

router.beforeEach((to, from, next) => {
  // 若用户想访问后台（以 /admin 为前缀的路由）
  // 未登录，则强制跳转登录页
  let token = getToken()
  if (!token && to.path.startsWith("/admin")) {
    showMessage("请先登录", "warning")
    next({ path: "/login" })
  } else {
    next()
  }
})
```

在`main.js`中引入路由守卫：
```js

```

### 12.2、后置路由守卫更改页面标题

修改`permission.js`内容，添加路由后置守卫：

```js
router.afterEach((to, from) => {
  document.title = (to.meta.title || "") + " - Weblog"
})
```

## 十三、引入 nprogress 实现页面顶部加载效果

### 13.1、安装依赖并引入样式

```cmd
pnpm i nprogress
```

在`main.js`中引入`CSS`文件：

```js
import "nprogress/nprogress.css"
```

### 13.2、封装工具类

在`utils`目录下新建`nprogress.js`文件，封装加载效果相关的代码：

```js
import nprogress from "nprogress"

export function showPageLoading() {
  nprogress.start()
}

export function hidePageLoading() {
  nprogress.done()
}
```

### 13.3、使用

在路由前置守卫中显示加载效果：

```js
router.beforeEach((to, from, next) => {
  showPageLoading()
  // 若用户想访问后台（以 /admin 为前缀的路由）
  // 未登录，则强制跳转登录页
  let token = getToken()
  if (!token && to.path.startsWith("/admin")) {
    showMessage("请先登录", "warning")
    next({ path: "/login" })
  } else {
    next()
  }
})
```

编辑`App.vue`文件，自定义加载颜色：

```vue
<style>
#nprogress .bar {
  background: #409eff !important;
}
</style>
```

在路由后置守卫中关闭加载效果：

```js
router.afterEach((to, from) => {
  document.title = (to.meta.title || "") + " - Weblog"
  hidePageLoading()
})
```

## 十四、前端登录模块优化

### 14.1、密码框切换明文图标及功能

给`ElementPlus`输入密码框组件添加`show-passowrd`属性：

```vue
<template>
	<!-- 省略 -->
	<el-input v-model="loginForm.password" size="large" type="password" placeholder="请输入密码" :prefix-icon="Lock" clearable show-password />
</template>
```

### 14.2、重复跳转登录页问题

修改路由前置守卫代码，新增判断用户已登录却要访问登录页的情况，提示用户请勿重复登录并跳转至后台首页：

```js
router.beforeEach((to, from, next) => {
  showPageLoading()
  // 若用户想访问后台（以 /admin 为前缀的路由）
  // 未登录，则强制跳转登录页
  let token = getToken()
  if (!token && to.path.startsWith("/admin")) {
    showMessage("请先登录", "warning")
    next({ path: "/login" })
  } else if (token && to.path ==="/login") {
    showMessage("请勿重复登录局", "warning")
    next({ path: "/admin/index" })
  }
  else {
    next()
  }
})
```

## 十五、创建仅支持查询操作的演示账号

### 15.1、创建角色表

执行以下`ddl`语句：

```sql
CREATE TABLE `t_user_role` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`username` VARCHAR(60) NOT NULL COMMENT '用户名',
	`role` VARCHAR(60) NOT NULL COMMENT '角色',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`) USING BTREE,
	KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';
```

> 该表以`username`字段关联`t_user`，与用户表为多对多的关系，即一个用户可能有多个角色，一个角色可能被多位用户持有

### 15.2、新增 test 用户以及角色

在用户表中新增一个`test`用户，该用户仅支持查询操作，密码为123456：

```sql
INSERT INTO `weblog`.`t_user` (`username`, `password`, `create_time`, `update_time`, `is_deleted`) VALUES('test', '$2a$10$8hGbnjKfLFL2JsvC9.Q1FOO3WhGQGM/7nHFH8PTiuCfyMPhwR0YD2', now(), now(), 0);
```

> 密码的密文运行`PasswordEncoderConfig`类中的`main`方法可以得到

**新增角色对应记录**

在角色表中插入两个角色：`admin`账号对应管理员；`test`账号对应游客：

```sql
INSERT INTO `weblog`.`t_user_role` (`id`, `username`, `role`, `create_time`) VALUES(1, 'admin', 'ROLE_ADMIN', now());
INSERT INTO `weblog`.`t_user_role` (`id`, `username`, `role`, `create_time`) VALUES(2, 'test', 'ROLE_VISITOR', now());
```

### 15.3、查询角色

**创建`DO`类以及`Mapper`接口**

在`weblog-module-common`模块下的`/domain/dos`包中，创建对应的`UserRoleDO`类：

```java
package com.cm.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 用户角色实体类
 */
@Data
@Builder
@TableName("t_user_role")
public class UserRoleDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    private String role;
    
    private Date createTime;
}

```

在`mapper`包下，创建`UserRoleMapper`接口：

```java
package com.cm.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.weblog.common.domain.dos.UserRoleDO;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
    /**
     * 根据用户名查询角色
     * @param username 用户名
     * @return 角色列表
     */
    default List<UserRoleDO> selectByUsername(String username) {
        LambdaQueryWrapper<UserRoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRoleDO::getUsername, username);

        return selectList(queryWrapper);
    }
}

```

**添加查询角色的逻辑**

修改`UserDetailServiceImpl`类中的代码，添加通过用户名查询角色的逻辑：

```java
package com.cm.weblog.jwt.service;

import com.cm.weblog.common.domain.dos.UserDO;
import com.cm.weblog.common.domain.dos.UserRoleDO;
import com.cm.weblog.common.domain.mapper.UserMapper;
import com.cm.weblog.common.domain.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现类
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 暂时先写死
        /*return User.withUsername("admin")
                .password("$2a$10$nL1x8aqM.Lfm..AYXiFVFeBBUU.Vjinc9NCSqoRrnw7E.F9s10Mxe")
                .authorities("ADMIN")
                .build();*/

        // 改为从数据库中查询
        UserDO userDO = userMapper.findByUsername(username);

        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        /*return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities("ADMIN") // 暂时先写死为 ADMIN
                .build();*/
        
        /*
        * 查询用户角色
        * */
        List<UserRoleDO> roleDOS = userRoleMapper.selectByUsername(username);
        String[] roleArr = new String[0];
        
        if (!CollectionUtils.isEmpty(roleDOS)) {
            roleArr = roleDOS.stream().map(UserRoleDO::getRole).toArray(String[]::new);
        }
        
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr)
                .build();
    }
}

```

### 15.4、改善权限不足的响应参数

**新增枚举值**

在`ResponseCodeEnum`中新增权限不够的枚举：

```java
FORBIDDEN("20004", "演示账号仅支持查询操作！")
```

**修改处理失败的自定义处理器**

修改`RestAccessDeniedHandler`的代码，增加权限不够的提示：

```java
package com.cm.weblog.jwt.handler;

import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.utils.Response;
import com.cm.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户权限不足处理器
 */
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("该用户暂无权限：", accessDeniedException);

        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.FORBIDDEN));
    }
}

```

### 15.5、启用 Spring Security 方法级注解

在`weblog-module-common`模块中的`pom.xml`中添加依赖：

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

编辑`WebSecurityConfig`配置类，添加`@EnableGlobalMethodSecurity`注解：

```java
/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // ... 省略
}
```

### 15.6、全局捕获异常

在全局异常捕获类`GlobalExceptionHandler`中添加方法手动抛出异常`AccessDeniedException`：

```java
@ExceptionHandler({ AccessDeniedException.class })
public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
	log.info("============= 捕获到 AccessDeniedException ============");
	throw e;
}
```

### 15.7、添加鉴权注解

在`TestController`控制器中添加一个`/admin/update`的`GET`接口，并添加注解以在调用之前先鉴权，必须是拥有管理员角色的账号发来的请求才允许正常执行：

```java
@GetMapping("/admin/update")
@ApiOperationLog(description = "测试更新接口")
@ApiOperation(value = "测试更新接口")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public Response<?> testUpdate() {
	log.info("更新成功...");
	return Response.success();
}
```

### 15.8、测试

用管理员账号发送请求，响应如下：

```json
{
    "success": true,
    "message": null,
    "code": null,
    "data": null
}
```

用游客账号发送请求，响应如下：

```json
{
    "success": false,
    "message": "演示账号仅支持查询操作！",
    "code": "20004",
    "data": null
}
```

