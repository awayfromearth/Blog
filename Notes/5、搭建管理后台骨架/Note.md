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

**在`Admin.vue`中组合这些组件**

```vue
<script setup>
import AdminHeader from "@/layouts/admin/components/AdminHeader.vue"
import AdminMenu from "@/layouts/admin/components/AdminMenu.vue"
import AdminTagsList from "@/layouts/admin/components/AdminTagsList.vue"
import AdminFooter from "@/layouts/admin/components/AdminFooter.vue"
</script>

<template>
  <!-- 外层容器 -->
  <el-container>

    <!-- 左边侧边栏 -->
    <el-aside>
      <AdminMenu />
    </el-aside>

    <!-- 右边主内容区域 -->
    <el-container>
      <!-- 顶栏容器 -->
      <el-header>
        <AdminHeader />
      </el-header>

      <el-main>
        <AdminTagsList />

        <!-- 主内容（根据路由动态展示不同页面） -->
        <router-view />
      </el-main>

      <!-- 底栏容器 -->
      <el-footer>
        <AdminFooter />
      </el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>

</style>
```

### 1.3、改造后台路由

```js
import Index from "@/pages/frontend/index.vue"
import Login from "@/pages/admin/Login.vue"
import AdminIndex from "@/pages/admin/index.vue"
import Admin from "@/layouts/admin/Admin.vue"

const routes = [
    {
        path: "/", // 路由地址
        component: Index, // 对应组件
        meta: { // meta 信息
            title: "Weblog 首页" // 页面标题
        }
    },
    {
        path: "/login",
        component: Login,
        meta: {
            title: "Weblog 登录页"
        }
    },
    {
       path: "/admin",
        component: Admin,
        children: [
            {
                path: "/admin/index", // 后台首页
                component: AdminIndex,
                meta: {
                    title: "Admin 后台首页"
                }
            }
        ]
    }
]

export default routes
```

### 1.4、测试

运行项目查看效果：

![](images/0.png)

