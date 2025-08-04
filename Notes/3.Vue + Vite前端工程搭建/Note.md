# 使用Vue3 + Vite 搭建前端工程

## 一、初始化脚手架

```shell
npm create vite@latest
```

![](images/0.png)

清理多余文件(示意图略)

## 二、整合vue-router

在命令行中，执行如下命令，安装 `vue-router`:

```shell
npm install vue-router
```

**配置 Router **

在`/src`目录下，创建`/pages`文件夹，在`/pages`文件夹下再创建两个文件夹，分别为：

- `/admin`: 管理后台
- `/frontend`: 前台

在`/frontend`目录下创建index.vue文件，代码如下：

```vue
<template>
	<h1>
        首页
    </h1>
</template>
```

在`/src`目录下，创建`/router`文件夹，在此文件夹下创建`index.js`，代码如下：

```js
import Index from "../pages/frontend/index.vue"
import { createRouter, createWebHashHistory } from "vue-router"

const routes = [
    {
        path: "/",
        component: Index,
        meta: {
            title: "Weblog 首页"
        }
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
```

在`App.vue`中使用`router-view`动态渲染路由对应组件：

```vue
<script setup>
</script>

<template>
  <router-view />
</template>

<style scoped>
</style>
```

在`main.js`中将Router导入并添加到Vue `app`实例中，代码如下：

```js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(router)
app.mount('#app')
```

**启动项目查看效果**

## 三、Vite配置路径别名

略