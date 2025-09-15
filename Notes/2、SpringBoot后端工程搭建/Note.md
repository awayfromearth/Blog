# SpringBoot 后端工程搭建

## 一、搭建 SpringBoot 多模块工程

### 1.1、新建项目  weblog-springboot

![](images/0.png)

> 默认的官方链接可能无法创建 `Java8`的项目：
>
> ![](images/1.png)

点击`Next`进行下一步：

![](images/2.png)

点击`Create`创建项目，创建成功删除多余文件（夹），最终文件目录如下：

![](images/3.png)

整理该模块的`pom.xml`，内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <!-- 切换 SpringBoot 版本号为2.6 -->
        <version>2.6.3</version>
        <relativePath /> <!-- 不依赖本地，从远程仓库拉取 -->
    </parent>

    <groupId>com.cm</groupId>
    <artifactId>weblog-springboot</artifactId>
    <version>${revision}</version>
    <name>weblog-springboot</name>
    <description>前后端分离博客项目</description>

    <!-- 多模块项目父工程打包模式必须指定为pom -->
    <packaging>pom</packaging>

    <!-- 子模块管理 -->
    <modules>
    </modules>

    <!-- 版本号统一管理 -->
    <properties>
        <!-- 项目版本号 -->
        <revision>0.0.1-SNAPSHOT</revision>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <!-- Maven相关 -->
        <maven.complier.source>${java.version}</maven.complier.source>
        <maven.complier.target>${java.version}</maven.complier.target>
    </properties>

    <!-- 统一依赖管理-->
    <dependencyManagement>
    </dependencyManagement>

    <build>
        <!-- 统一插件管理 -->
        <pluginManagement>
        </pluginManagement>
    </build>


    <!-- 使用阿里云的 Maven 仓库源提升包下载速度 -->
    <repositories>
        <repository>
            <id>aliyunmaven</id>
            <name>aliyun</name>
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
    </repositories>
</project>

```

### 1.2、创建 Web 访问（打包）模块

在父项目上右击，添加`Module`：

![](images/4.png)

点击`Next`进行下一步：

![](images/5.png)

点击`Create`创建子模块`weblog-web`，并删除多余文件（夹），最终文件目录如下：

![](images/6.png)

整理该模块的`pom.xml`，内容如下：

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

    <artifactId>weblog-web</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>weblog-web</name>
    <description>入口模块 负责博客前台展示、打包</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 免写冗余的 Java 样板式代码 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- 单测 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 1.3、创建 Admin  后台管理功能模块

在父项目上右击，添加`Module`：

![](images/9.png)

点击`Next`进行下一步：

![](images/10.png)

点击`Create`创建子模块`weblog-module-admin`，并删除多余文件（夹），最终文件目录如下：

![](images/11.png)

整理该模块的`pom.xml`，内容如下：

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

    <artifactId>weblog-module-admin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>weblog-module-admin</name>
    <description>管理后台相关功能</description>

    <dependencies>
        <!-- 免写冗余的 Java 样板式代码 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- 单测 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>

```

### 1.4、创建  Common 公共功能模块

在父项目上右击，添加`Module`：

![](images/12.png)

点击`Next`进行下一步：

![](images/10.png)

点击`Create`创建子模块`weblog-module-admin`，并删除多余文件（夹），最终文件目录如下：

![](images/13.png)

整理该模块的`pom.xml`，内容如下：

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

    <artifactId>weblog-module-common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>weblog-module-common</name>
    <description>存放通用功能</description>

    <dependencies>
        <!-- 免写冗余的 Java 样板式代码 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- 常用工具库 -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>

        <!-- 单测 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>

```

### 1.5、补充要用的依赖包以及各模块之间的依赖关系

**weblog-springboot**

`pom.xml`

```xml
	...省略

	<!-- 子模块管理 -->
    <modules>
        <!-- 入口模块 -->
        <module>weblog-web</module>
        <!-- 管理后台 -->
        <module>weblog-module-admin</module>
        <!-- 通用模块 -->
        <module>weblog-module-common</module>
    </modules>

    <!-- 版本号统一管理 -->
    <properties>
        ...省略
       	<!-- 依赖包版本 -->
        <lombok.version>1.18.28</lombok.version>
        <guava.version>31.1-jre</guava.version>
        <commons-lang3.version>3.12.0</commons-lang3.version>
    </properties>

    <!-- 统一依赖管理-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.cm</groupId>
                <artifactId>weblog-module-admin</artifactId>
                <version>${revision}</version>
            </dependency>

            <dependency>
                <groupId>com.cm</groupId>
                <artifactId>weblog-module-common</artifactId>
                <version>${revision}</version>
            </dependency>

            <!-- 常用工具库 -->
            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>${guava.version}</version>
            </dependency>

            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${commons-lang3.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <!-- 统一插件管理 -->
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <!-- 排除lombok在最终构建产物中 -->
                        <excludes>
                            <exclude>
                                <groupId>org.projectlombok</groupId>
                                <artifactId>lombok</artifactId>
                            </exclude>
                        </excludes>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>


    <!-- 使用阿里云的 Maven 仓库源提升包下载速度 -->
    <repositories>
        <repository>
            <id>aliyunmaven</id>
            <name>aliyun</name>
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
    </repositories>
</project>

```

**weblog-web**：依赖 `Admin`包和`Common`包

`pom.xml`

```xml
	...省略
	<dependencies>
        <dependency>
            <groupId>com.cm</groupId>
            <artifactId>weblog-module-common</artifactId>
        </dependency>

        <dependency>
            <groupId>com.cm</groupId>
            <artifactId>weblog-module-admin</artifactId>
        </dependency>

        ...省略
    </dependencies>

```

**weblog-module-admin**：依赖`Common`包

`pom.xml`

```xml
	...省略
	<dependencies>
        <dependency>
            <groupId>com.cm</groupId>
            <artifactId>weblog-module-common</artifactId>
        </dependency>

        ...省略
    </dependencies>

```

### 备注：解决 pom.xml 黄色波浪线警告

![](images/8.png)

### 1.6、测试

刷新`Maven`下载依赖包：

![](images/7.png)

选中跳过测试的按钮：

![](images/14.png)

打包：

![](images/15.png)

打包成功会在`target`目录下输出`jar`包

启动项目：启动`weblog-springboot`的`WeblogWebApplication`类：

![](images/16.png)

## 二、项目多环境配置

### 2.1、配置 weblog-springboot 环境

修改`pom.xml`，内容如下：

```xml
<project>
	...省略
    
	<profiles>
        <profile>
            <id>dev</id>
            <properties>
                <env>dev</env>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>

        <profile>
            <id>prod</id>
            <properties>
                <env>prod</env>
            </properties>
        </profile>
    </profiles>
    
</project>
```

### 2.2、新建环境配置文件

![](images/19.png)

### 2.3、测试

切换环境：

![](images/18.png)

输出结果：

![](images/17.png)

## 三、整合 Logback 日志

### 3.1、引入依赖

由于之前在`weblog-web`模块中已引入过`spring-boot-starter-web`依赖，它会自动包含`Logback`相关依赖，所以无需再额外添加依赖

### 3.2、配置

在`weblog-web/src/main/resources`目录下新建文件`logback-weblog.xml`配置文件，内容如下：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <jmxConfigurator />
    <include resource="org/springframework/boot/logging/logback/defaults.xml" />

    <property scope="context" name="appName" value="weblog" />
    <!-- 自定义日志输出路径及名称前缀-->
    <property name="LOG_FILE" value="/app/weblog/logs/${appName}.%d{yyyy-MM-dd}" /> <!-- 输出控制台路径 -->
    <!-- <property name="LOG_FILE" value="D:\\GitRepository\\Projects\\Blog\\Back\\weblog-springboot\\logs\\${appName}.%d{yyyy-MM-dd}" /> --> <!-- 输出文件路径 -->
    <!-- 格式化输出：%d 表示日期，%thread 表示线程，%-5level表示从左显示5个字符宽度， %msg% 表示日志消息  -->
    <property name="FILE_LOG_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n" />

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志输出文件名 -->
            <FileNamePattern>${LOG_FILE}-%i.log</FileNamePattern>
            <!-- 日志保留天数-->
            <MaxHistory>30</MaxHistory>
            <!-- 日志文件最大大小 -->
            <TimeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </TimeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>

        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!-- 格式化输出： -->
            <pattern>${FILE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>

    <!-- dev环境下日志仅输出到控制台 -->
    <springProfile name="dev">
        <include resource="org/springframework/boot/logging/logback/console-appender.xml" />
        <root level="info">
            <appender-ref ref="CONSOLE" />
        </root>
    </springProfile>

    <!-- PROD环境下日志输出到文件 -->
    <springProfile name="prod">
        <include resource="org/springframework/boot/logging/logback/console-appender.xml" />
        <root level="INFO">
            <appender-ref ref="FILE" />
        </root>
    </springProfile>
</configuration>
```

由于输出日志到文件只需在生产环境开启，所以仅需在生产环境`application-prod.yml`配置

```yml
# 日志
logging:
  config: classpath:logback-weblog.xml
```

### 3.3、测试

在单元测试包下的`WeblogWebApplicationTests`类中新增一个`testLog`测试方法以及`@Slf4j`注解，代码如下：

```java
package com.cm.weblog.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j // 自动生成日志实例
class WeblogWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testLog() {
        log.info("这是一行 INFO 级别日志");
        log.warn("这是一行 WARN 级别日志");
        log.error("这是一行 ERROR 级别日志");

        // 占位符
        String author = "CM";
        log.info("这是一行带有占位符的日志记录，作者：{}", author);
    }
}

```

在`dev`环境下运行测试方法，结果如下：

![](images/20.png)

在`prod`环境下运行测试方法，日志成功输出到指定文件

> 测试完记得把环境改回`dev`

## 四、自定义注解实现 AOP 请求日志切面

### 4.1、引入依赖

父项目中添加`jackson`工具版本号及依赖管理，它用于将出入参转为`JSON`字符串

```xml
<!-- 版本号统一管理 -->
<properties>
    ...省略
    <jackson.version>2.15.2</jackson.version>
</properties>

 <!-- 统一依赖管理-->
<dependencyManagement>
    <dependencies>
    	...省略
        <dependency>
        	<groupId>com.fasterxml.jackson.core</groupId>
        	<artifactId>jackson-databind</artifactId>
        	<version>${jackson.version}</version>
       	</dependency>

        <dependency>
        	<groupId>com.fasterxml.jackson.core</groupId>
        	<artifactId>jackson-core</artifactId>
        	<version>${jackson.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

在`weblog-module-common`中引用依赖：

```xml
...省略
<!-- AOP 切面 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>

<!-- Jackson -->
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
</dependency>

<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-core</artifactId>
</dependency>
```

### 4.2、自定义注解

在`weblog-module-common`模块下新建`aspect`包用于放置切面相关的功能类，在其中创建一个`ApiOpeationLog`的注解，内容如下：

```java
package com.cm.weblog.common.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // 指定注解在运行时保留(可以通过反射在运行时被访问和解析)
@Target({ElementType.METHOD}) // 指定该注解作用于方法
@Documented // 生成文档时注解元素及注解信息会被包含
public @interface ApiOperationLog {
    /**
     * API 功能描述
     * @return String
     */
    String description() default "";
}

```

### 4.3、创建 JSON 工具类

在`weblog-module-common`模块下新建`utils`包，在其中新建一个`JsonUtil`类，用于转成`JSON`字符串，内容如下：

```java
package com.cm.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 工具类
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }
}
```

### 4.4、自定义切面类

在`aspect`包下新建`ApiOperationLogAspect`类，代码如下：

```java
package com.cm.weblog.common.aspect;

import com.cm.weblog.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 自定义切面类
 */
@Aspect // 声明该类为一个切面类
@Component
@Slf4j
public class ApiOperationLogAspect {
    /**
     * 以自定义 @ApiOperationLog 注解为切点
     * 凡是添加该注解的方法都会执行环绕中的代码
     */
    @Pointcut("@annotation(com.cm.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog() {}

    /**
     * 环绕
     * @param joinPoint 切点
     * @return 请求结果
     * @throws Throwable 异常
     */
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();

            MDC.put("traceId", UUID.randomUUID().toString());

            // 获取处理请求的类和方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            // 获取请求入参并转成 JSON 字符串
            Object[] args = joinPoint.getArgs();
            String argsJson = Arrays.stream(args).map(toJsonString()).collect(Collectors.joining(", "));

            // 功能描述信息
            String description = getApiOperationLogDescription(joinPoint);

            // 打印入参
            log.info("====== 请求开始：[{}]，入参：{}，请求类：{}, 请求方法：{} ============================ ", description, argsJson, className, methodName);

            // 执行切点方法
            Object result = joinPoint.proceed(args);

            // 计算执行耗时
            long executionTime = System.currentTimeMillis() - startTime;

            // 打印出参
            log.info("====== 请求结束：[{}]，耗时：{}ms， 出参：{} ============================ ", description, executionTime, JsonUtil.toJson(result));

            return result;
        } finally {
            MDC.clear();
        }
    }

    /**
     * 转 JSON
     * @return Function
     */
    private Function<Object, String> toJsonString() {
        return JsonUtil::toJson;
    }

    /**
     * 获取注解的描述信息
     * @param joinPoint 切点
     * @return 注解描述
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取被注解的方法
        Method method = methodSignature.getMethod();

        // 提取注解
        ApiOperationLog annotation = method.getAnnotation(ApiOperationLog.class);

        // 提取 description 属性
        return annotation.description();
    }
}

```

### 4.5、添加包扫描

在`weblog-web`的启动类中添加包扫描

```java
package com.cm.weblog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cm.weblog.*"}) // 多模块项目中必须手动指定要扫描的包下面的所有类
public class WeblogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogWebApplication.class, args);
    }

}

```

### 4.6、测试

在`weblog-web`模块下新建`model`包存储`pojo`对象，新建`User`类用于测试：

```java
package com.cm.weblog.web.model;

import lombok.Data;

@Data
public class User {
    private String username;
    private Integer sex;
}

```

在`weblog-web`模块下新建`controller`包，新建`TestController`类测试请求，内容如下：

```java
package com.cm.weblog.web.controller;

import com.cm.weblog.common.aspect.ApiOperationLog;
import com.cm.weblog.web.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试请求类
 */
@RestController
public class TestController {
    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public User test(@RequestBody User user) {
        return user;
    }
}

```

发送请求：

**入参**：

```json
{
    "username": "昌帅",
    "sex": 2
}
```

**出参**：

```json
{
    "username": "昌帅",
    "sex": 2
}
```

**打印日志**：

```json
请求开始：[测试接口]，入参：{"username":"昌帅","sex":25}，请求类：TestController, 请求方法：test ============================ 
请求结束：[测试接口]，耗时：1ms， 出参：{"username":"昌帅","sex":25} ============================ 
```

