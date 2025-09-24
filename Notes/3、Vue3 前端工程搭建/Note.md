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

## 二、配置别名

修改`vite.config.js`配置

```js

```

## 三、整合路由

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

在`main.js`中引入并注册路由：

```js
import { createApp } from 'vue'

import router from "./router/index"
import App from './App.vue'

const app = createApp(App)
app.use(router)
app.mount('#app')

```

在`App.vue`中添加`router-view`组件：

```vue
<template>
  <router-view />
</template>
```

运行项目，访问`http://localhost:5176/#/`查看效果：

![](images/1.png)

## 四、整合 Tailwind CSS

### 4.1、安装依赖及配置

安装依赖：

```cmd
pnpm i tailwindcss@3 postcss autoprefixer -D
```

生成配置文件：

```cmd
npx tailwindcss init -p
```

执行命令后生成两个配置文件：

- `tailwind.config.js`：定制`Tailwind`的默认设置，比如框架的颜色、字体、断点、间距
- `postcss.config.js`：配置`PostCSS`(`CSS`处理工具)

修改`tailwind.config.js`配置文件，添加所有模板文件的路径：

```js
/** @type {import('tailwindcss').Config} */
export default {
  content: [
      "./index.html",
      "./src/**/*.{vue,js,ts,jsx,tsx}"
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

```

### 4.2、在项目中使用 Tailwind

在`src/assets/styles`目录下新建`main.css`文件，引入`tailwind`样式：

```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

在`main.js`中引入：

```js
import "@/assets/styles/main.css"
```

### 4.3、在项目中测试 Tailwind 的使用

修改`frontend/index.vue`文件，测试`Tailwind`的使用：

```vue
<template>
  <div class="bg-green-300 inline">绿色</div>
  <div class="bg-yellow-300 ml-2 inline">黄色</div>
  <div class="bg-blue-300 ml-2 inline">蓝色</div>
</template>
```

效果如下：

![](images/2.png)

## 五、整合 Tailwind CSS 组件库 Flowbite

### 5.1、安装依赖及配置

安装依赖：

```cmd
pnpm i flowbite@1.8.1
```

在`tailwind.config.js`文件中添加`Flowbite`插件：

```js
export default {
    // ...省略
    plugins: [
        require("flowbitw/plugin")
    ],
    content: [
        "./node_modules/flowbite/**/*.js"
    ]
}
```

### 5.2、在项目中使用 Flowbite

在`frontend/index.vue`文件中创建`Navbar`：

```vue

```

在`main.js`中引入：

```js
import "@/assets/styles/main.css"
```

在`main.css`中修改`body`的基本样式：

```css

```

运行项目，效果如下：

![]()

## 六、整合 ElmentPlus 组件库

### 6.1、安装依赖及配置

安装依赖：

```cmd
pnpm i element-plus
```

配置自动导入：

```cmd
pnpm i unplugin-vue-components unplugin-auto-import -D
```

修改`viite.config.js`配置：

```js

```

### 6.2、测试

为上文`Navbar`中的登录按钮添加路由跳转事件：

```vue

```

添加登录路由：

```js
// ...省略
const routes = [
    {
        path: "/login",
        component: Login,
        meta: {
            title: "Weblog 登录页"
        }
    }
]
```

在登录页中试用`ElementPlus`组件：

```vue

```

