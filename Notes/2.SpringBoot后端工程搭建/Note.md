# 一、搭建Spring Boot多模块工程(Spring Initializr)

## 1.1、IDEA搭建多模块工程骨架

**创建项目**

首先选择一个位置创建Back目录用于存放后端项目、以及Front目录存放前端项目

**创建父项目**

![](images/0.png)

![](images/1.png)

删除无用文件及目录，最终结构如下：

![](images/2.png)

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

## 1.2、创建 web 模块(访问 + 打包模块)

在父项目上右键，添加模块Module

![](images/3.png)

![](images/4.png)

删除多余文件，最终目录结构：

![](images/5.png)

在父项目中添加子模块及spring-boot-maven-plugin

```xml
<!-- 子模块管理 -->
<modules>
    <!-- 入口模块 -->
    <module>weblog-web</module>
</modules>

<build>
    <!-- 统一插件管理 -->
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
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
```

编辑weblog-web中的pom.xml

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
    <name>weblog-web</name>
    <description>weblog-web</description>

    <dependencies>
        <!-- Web依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 免写冗余的Java样板式代码 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!-- 单元测试 -->
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

> 消除IDEA中pom.xml的黄色波浪线
>
> 1. 打开 **Settings**（设置），可以同时按 *Ctrl+Alt+S*。
>
> 2. 搜索 **Inspections**。
>
> 3. 找到 **Security** 下的两项并取消勾选。
>
>    ![](images/6.png)
>
> 4. 点击 **Apply** 和 **OK**。

## 1.3、创建 Admin 管理后台功能模块

![](images/7.png)

![](images/8.png)

![](images/9.png)

在父项目中添加模块：

```xml
 <!-- 子模块管理 -->
    <modules>
        <!-- 入口模块 -->
        <module>weblog-web</module>
        <!-- 管理后台 -->
        <module>weblog-module-admin</module>
    </modules>
```

修改模块pom.xml：

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
    <name>weblog-module-admin</name>
    <description>weblog-admin(负责管理后台相关功能)</description>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- 单元测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>

```

## 1.4、创建 common 公共功能模块

与上面admin模块一样，最终pom.xml文件如下：

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
    <name>weblog-module-common</name>
    <description>weblog-common(此模块用于存放一些通用的功能)</description>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- 单元测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- 常用工具库 -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>
    </dependencies>

</project>

```

## 1.5、修改父模块依赖管理并修改子模块相互依赖关系

父模块：

```xml
<!-- 版本号统一管理 -->
<properties>
    <!-- 项目版本号 -->
    <revision>0.0.1-SNAPSHOT</revision>
    <java.version>1.8</java.version>
    <project.build.sourceEncoding>UFT-8</project.build.sourceEncoding>

    <!-- Maven 相关 -->
    <maven.compiler.source>${java.version}</maven.compiler.source>
    <maven.compiler.target>${java.version}</maven.compiler.target>

    <!-- 依赖包版本 -->
    <lombok.version>1.18.28</lombok.version>
    <guava.version>31.1-jre</guava.version>
    <commons-lang3.version>3.12.0</commons-lang3.version>
</properties>
```

```xml
<!-- 统一依赖管理 -->
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
```

web 模块：

```xml
<dependency>
    <groupId>com.cm</groupId>
    <artifactId>weblog-module-common</artifactId>
</dependency>

<dependency>
    <groupId>com.cm</groupId>
    <artifactId>weblog-module-admin</artifactId>
</dependency>
```

admin 模块：

```xml
<dependency>
    <groupId>com.cm</groupId>
    <artifactId>weblog-module-common</artifactId>
</dependency>
```

## 1.6、测试

![](images/13.png)

![](images/14.png)

![](images/15.png)

![](images/16.png)

