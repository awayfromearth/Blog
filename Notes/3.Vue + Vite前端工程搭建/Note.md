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

