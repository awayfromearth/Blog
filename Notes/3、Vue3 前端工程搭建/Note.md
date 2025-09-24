# Vue3 前端工程搭建

## 一、初始化脚手架

```cmd
pnpm create vite@latest
```

删除多余文件及多余的文件内容，最终文件结构如下：

![](images/0.png)

`App.vue`的内容如下：

```vue
<template>
</template>
```

`main.js`的内容如下：

```js
import { createApp } from 'vue'
import App from './App.vue'

createApp(App).mount('#app')

```

## 二、整合路由

### 2.1、安装 vue-router

```cmd
pnpm i vue-router
```

### 2.2、配置 vue-router

**准备工作：**

在`src`目录下新建`pages`目录用于存放页面相关代码，在`pages`目录下新建`admin`以及`frontend`目录

- `admin`：存放后台管理相关代码
- `frontend`: 存放前台展示相关代码

在`/frontend`文件夹下，创建`index.vue`首页文件，代码如下：

```vue
<template>
    <h1>首页</h1>
</template>
```

**配置路由：**

在`src`目录下新建`router`目录，在`router`目录下新建`routes.js`及`index.js`

- `routes.js`：存放实际路由元素对象
- `index.js`: 路由配置

初始化`routes.js`代码如下：

```js
const routes = [
  {
    path: "/", // 路由地址
    component: Index, // 对应组件
    meta: { // meta 信息
      title: "Weblog 首页" // 页面标题
    }
  }
]

export default routes
```

创建路由`index.js`代码如下：

```js
import Index from "@/pages/frontend/index.vue"
import routes from "./routes"

import { createRouter, createWebHashHistory } from "vue-router"

const router = createRouter({
  // 指定路由的历史管理方式，hash 模式指的是 URL 的路径是通过 hash 符号（#）进行标识
  history: createWebHashHistory(),
  routes, 
})

export default router

```

**测试路由切换：**