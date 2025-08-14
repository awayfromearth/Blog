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

修改`vite.config.js`代码如下：

```js
import { defineConfig } from 'vite'
import { fileURLToPath ,URL } from "node:url"
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)) // 对应当前文件所在目录下的src目录的绝对文件路径
    }
  }
})
```

## 四、整合 Tailwind CSS

### 4.1、安装

```shell
npm i -D tailwindcss@3 postcss autoprefixer
```

### 4.2、生成配置文件

```shell
npx tailwindcss init -p
```

执行完成后，生成

- `tailwind.config.js`
- postcss.config.js

### 4.3、配置模板路径

在`tailwind.config.js`文件中，添加所有模板文件的路径：

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

### 4.4、添加 Tailwind 指令

在`src`目录下新建`assets`目录，在此目录下新建`main.css`，内容如下：

```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

修改`main.js`，引入`main.css`

```js
import "@/assets/main.css"
```

### 4.5、测试

重新`npm run dev`，修改`/frontend/index.vue`

```vue
<template>
  <h1>
    首页
  </h1>
  <div class="bg-green-300 inline">绿色</div>
  <div class="bg-yellow-300 ml-2 inline">黄色</div>
  <div class="bg-blue-300 ml-2 inline">蓝色</div>
  <div class="text-xs sm:text-lg md:text-xl lg:text-2xl">响应式字体</div>
</template>
```

## 五、整合 Tailwind CSS 组件库：Flowbite

### 5.1、安装

```shell
npm i flowbite@1.8.1
```

在`tailwind.config.js`中添加`js`相关文件

```js
export default {
    // ...省略
    content: [
        "./node_modules/flowbite/**/*.js"
    ]
}
```

### 5.2、在`main.css`中添加`body`的基本样式

```css
@tailwind base;
@tailwind components;
@tailwind utilities;

body {
    font-family: -apple-system-font, BlinkMacSystemFont, "Helvetica Neue", PingFang SC, Hiragino Sans GB, "Microsoft YaHei UI", "Microsoft YaHei", "Helvetica Neue", Arial, sans-serif;
    color: #4c4e4d;
    font-size: 16px;
    background-color: #f4f4f4;
    line-height: 1.6;
}
```

### 5.3、使用Flowbite开发首页导航栏

`frontend/index.vue`代码如下：

```vue
<script setup>
import { onMounted } from 'vue'
import { initCollapses } from 'flowbite'

// 初始化 flowbite 相关组件
onMounted(() => {
  initCollapses();
})
</script>

<template>
  <nav class="bg-white border-gray-200 border-b dark:bg-gray-900">
    <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
      <a href="/" class="flex items-center">
        <img src="https://flowbite.com/docs/images/logo.svg" class="h-8 mr-3" alt="Flowbite Logo" />
        <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">犬小哈的博客</span>
      </a>
      <div class="flex items-center md:order-2">
        <button type="button" data-collapse-toggle="navbar-search" aria-controls="navbar-search"
                aria-expanded="false"
                class="md:hidden text-gray-500 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-700 focus:outline-none focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 rounded-lg text-sm p-2.5 mr-1">
          <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
               viewBox="0 0 20 20">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
          </svg>
          <span class="sr-only">Search</span>
        </button>
        <div class="relative hidden mr-2 md:block">
          <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
            <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                 xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
            </svg>
            <span class="sr-only">Search icon</span>
          </div>
          <input type="text" id="search-navbar"
                 class="block w-full p-2 pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                 placeholder="请输入关键词...">
        </div>

        <!-- 登录 -->
        <div class="text-gray-900 ml-1 mr-1 hover:text-blue-700 dark:text-gray-100 cursor-pointer">登录</div>

        <button data-collapse-toggle="navbar-search" type="button"
                class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
                aria-controls="navbar-search" aria-expanded="false">
          <span class="sr-only">Open main menu</span>
          <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
               viewBox="0 0 17 14">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M1 1h15M1 7h15M1 13h15" />
          </svg>
        </button>
      </div>
      <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-search">
        <div class="relative mt-3 md:hidden">
          <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
            <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                 xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
            </svg>
          </div>
          <input type="text" id="search-navbar"
                 class="block w-full p-2 pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                 placeholder="Search...">
        </div>
        <ul
            class="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
          <li>
            <a href="#"
               class="block py-2 pl-3 pr-4 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 md:dark:text-blue-500"
               aria-current="page">首页</a>
          </li>
          <li>
            <a href="#"
               class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">分类</a>
          </li>
          <li>
            <a href="#"
               class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">标签</a>
          </li>
          <li>
            <a href="#"
               class="block py-2 pl-3 pr-4 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">归档</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
```

## 六、整合 Element Plus 组件库

### 6.1、安装

```shell
npm i element-plus
```

### 6.2、配置自动导入

安装`unplugin-vue-components` 和 `unplugin-auto-import`插件

```shell
npm i -D unplugin-vue-components unplugin-auto-import
```

然后把下列代码插入到配置文件 `vite.config.js` 中

```js
import { defineConfig } from 'vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

export default defineConfig({
  // ...
  plugins: [
    // ...
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
})
```

完成上面工作后，在控制台 执行 `npm run dev` 重新运行一下项目

### 6.3、测试

**新增一个登录页**

在 `/pages/admin/` 中新建一个 `login.vue` 登录页面

```vue
<template>
    <div class="p-2">
        <h1>登录页</h1>
        <el-row class="mb-4">
            <el-button>Default</el-button>
            <el-button type="primary">Primary</el-button>
            <el-button type="success">Success</el-button>
            <el-button type="info">Info</el-button>
            <el-button type="warning">Warning</el-button>
            <el-button type="danger">Danger</el-button>
        </el-row>

        <el-row class="mb-4">
            <el-button plain>Plain</el-button>
            <el-button type="primary" plain>Primary</el-button>
            <el-button type="success" plain>Success</el-button>
            <el-button type="info" plain>Info</el-button>
            <el-button type="warning" plain>Warning</el-button>
            <el-button type="danger" plain>Danger</el-button>
        </el-row>

        <el-row class="mb-4">
            <el-button round>Round</el-button>
            <el-button type="primary" round>Primary</el-button>
            <el-button type="success" round>Success</el-button>
            <el-button type="info" round>Info</el-button>
            <el-button type="warning" round>Warning</el-button>
            <el-button type="danger" round>Danger</el-button>
        </el-row>

    </div>
</template>
```

**添加路由**

在 `/router/index.js` 中添加对应的路由

```js
import Login from '@/pages/admin/login.vue'

// 统一在这里声明所有路由
const routes = [
    // 省略...
    {
        path: '/login', // 登录页
        component: Login,
        meta: {
            title: 'Weblog 登录页'
        }
    },
]
    // 省略...
```

**跳转**

编辑 `/pages/frontend/index.vue` 首页文件，为登录按钮添加点击事件，实现跳转功能

```vue
<!-- 登录 -->
<div class="text-gray-900 ml-1 mr-1 hover:text-blue-700" @click="$router.push('/login')">登录</div>
```

