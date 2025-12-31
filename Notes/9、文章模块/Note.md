# 文章模块开发

## 一、模块功能分析及表设计

### 1.1、前端功能分析

前端页面原型1：

![](images/0.jpg)

首先是文章列表页面，要实现四个功能：

- 分页条件查询文章列表
- 提供编辑入口
- 提供预览入口
- 删除文章



前端页面原型2：

![](images/1.png)

点击编辑或新增后弹出全屏的编辑文章对话框，要实现如下功能：

- 点击新增时显示空白内容的全屏文章编辑对话框
- 点击编辑时渲染对应的文章标题、摘要、内容(`Markdown`)、封面、分类、标签
- 保存

### 1.2、后端接口设计

根据前端所要实现的功能，后端大致需要提供如下接口：

- 分页查询文章列表
- 删除文章
- 发布文章
- 获取文章详情
- 更新文章

### 1.3、表设计

#### 1.3.1、文章表

分析完接口后，可设计此模块所需的表。因为为存储的文章内容数据文本较大且只有访问文章详情的时候才需要查询，将表拆分为两张，一张存储文章基础信息，一张存储文章内容以文章`id`作关联

文章基础信息表需要字段如下：

- id
- 标题
- 封面
- 摘要
- 创建时间
- 更新时间
- 删除标志位
- 被阅读次数

最终建表语句如下：

```sql
CREATE TABLE `t_article` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id',
	`title` VARCHAR(120) NOT NULL DEFAULT '' COMMENT '文章标题',
	`cover` VARCHAR(120) NOT NULL DEFAULT '' COMMENT '文章封面',
	`suammry` VARCHAR(160) DEFAULT '' COMMENT '文章摘要',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	`is_deleted` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '删除标志位：0：未删除 1：已删除',
	`read_num` int(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '被阅读数',
	PRIMARY KEY (`id`) USING BTREE,
	KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文章表';
```

文章内容表需要字段如下：

- 文章内容`id`
- 文章`id`
- 文章内容正文

最终建表语句如下：

```sql
CREATE TABLE `t_article_content` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章内容id',
	`article_id` BIGINT(20) NOT NULL COMMENT '文章id',
	`content` TEXT COMMENT '文章正文',
	PRIMARY KEY (`id`) USING BTREE,
	KEY `idx_article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文章内容表';
```

#### 1.3.2、文章关联关系表

文章发布时还需选择文章归属的分类，每篇文章选择一个分类，因此还需建立文章与类别的关联表：

```sql
CREATE TABLE `t_article_category_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) unsigned NOT NULL COMMENT '文章id',
  `category_id` bigint(20) unsigned NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_article_id` (`article_id`) USING BTREE,
  KEY `idx_category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文章所属分类关联表';
```

> 因为原型页中，一个文章只能归属于一个分类，所以，这里对其添加了`UNIQUE KEY`唯一索引。

此外，每篇文章可以绑定多个标签，因此还需建立一个文章与标签的关联表：

```sql
CREATE TABLE `t_article_tag_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) unsigned NOT NULL COMMENT '文章id',
  `tag_id` bigint(20) unsigned NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_article_id` (`article_id`) USING BTREE,
  KEY `idx_tag_id` (`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文章对应标签关联表';
```