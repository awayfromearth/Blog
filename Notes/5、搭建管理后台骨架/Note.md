# 搭建管理后台骨架

## 一、搭建管理后台基本布局

### 1.1、搭建基础结构

在`src`目录下新建`/layouts/admin`目录，在该目录下新建`Admin.vue`文件，布局基本结构：

```vue
<script setup>

</script>

<template>
  <!-- 外层容器 -->
  <el-container>

    <!-- 左边侧边栏 -->
    <el-aside>左边侧边栏</el-aside>

    <!-- 右边主内容区域 -->
    <el-container>
      <!-- 顶栏容器 -->
      <el-header>头部</el-header>

      <el-main>
        标签导航栏

        <!-- 主内容（根据路由动态展示不同页面） -->
        <router-view />
      </el-main>

      <!-- 底栏容器 -->
      <el-footer>底部</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>

</style>
```

### 1.2、拆分组件

在 `/layouts/admin` 文件夹下，新建 `/components` 文件夹，用于放置后台组件

**头部组件**

新建`AdminHeader.vue`，代码如下：

```vue
<template>
    <div class="bg-emerald-700 h-[64px] text-white">
        头部
    </div>
</template>
```

**左边菜单栏**

新建 `AdminMenu.vue` , 代码如下：

```vue
<template>
    <div class="bg-slate-800 h-screen text-white">左边栏菜单</div>
</template>
```

**标签导航栏**

新建 `AdminTagList.vue`, 代码如下：

```vue
<template>
    <div class="bg-indigo-700 text-white">
        标签导航栏
    </div>
</template>
```

**页脚**

新建 `AdminFooter.vue` , 代码如下：

```vue
<template>
    <div class="bg-cyan-700 text-white">
        底部
    </div>
</template>
```