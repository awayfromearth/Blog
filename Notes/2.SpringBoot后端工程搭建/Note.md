# 一、搭建Spring Boot多模块工程(Spring Initializr)

## 1.1、IDEA搭建多模块工程骨架

**创建项目**

首先选择一个位置创建Back目录用于存放后端项目、以及Front目录存放前端项目

**创建父项目**

![](images/0.png)

![](images/1.png)

删除无用文件及目录，最终结构如下：

![](images/3.png)

接下来，开始整理一下父项目的 `pom.xml` 文件，整理后内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project
        xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
>
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <!-- 将 Spring Boot 的版本号切换成 2.6 版本 -->
        <version>2.6.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.cm</groupId>
    <artifactId>weblog-springboot</artifactId>
    <version>${revision}</version>
    <name>weblog-springboot</name>
    <description>前后端分离博客 Weblog By CM</description>

    <!-- 多模块项目父工程打包模式必须指定为 pom -->
    <packaging>pom</packaging>

    <!-- 子模块管理 -->
    <modules>
    </modules>

    <!-- 版本号统一管理 -->
    <properties>
        <!-- 项目版本号 -->
        <revision>0.0.1-SNAPSHOT</revision>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UFT-8</project.build.sourceEncoding>

        <!-- Maven 相关 -->
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
    </properties>

    <!-- 统一依赖管理 -->
    <dependencyManagement>
    </dependencyManagement>

    <build>
        <!-- 统一插件管理 -->
        <pluginManagement>
        </pluginManagement>
    </build>

    <!-- 使用阿里云的 Maven 仓库源，提升包下载速度 -->
    <repositories>
        <repository>
            <id>aliyunmaven</id>
            <name>aliyun</name>
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
    </repositories>
</project>
```