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

## 二、文件上传接口

### 2.1、添加依赖

首先在父项目的`pom.xml`中添加`Minio`版本管理及声明：

```xml
<!-- 版本管理 -->
<properties>
	<minio.version>8.2.1</minio.version>
</properties>

<!-- 以来管理 -->
<dependencies>
	<dependency>
    	<groupId>io.minio</groupId>
        <artifactId>minio</artifactId>
        <version>${minio.version}</version>
    </dependency>
</dependencies>
```

然后在`weblog-modue-admin`模块中添加该依赖：

```xml
<dependency>
	<groupId>io.minio</groupId>
	<artifactId>minio</artifactId>
</dependency>
```

