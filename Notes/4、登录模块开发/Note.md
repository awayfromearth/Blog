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
        jwtParser = Jwts.parserBuilder().requireAudience(issuer)
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