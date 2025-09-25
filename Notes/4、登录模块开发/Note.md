# 登录模块开发

## 一、登录页面开发

### 1.1、基本布局

修改`/pages/admin/Login.vue`的内容，实现一个grid 网格布局的基本骨架：

```vue
<template>
  <!-- 使用 grid 网格布局，并指定列数为 2，高度占满全屏 -->
  <div class="grid grid-cols-2 h-screen">
    <!-- 默认先适配移动端，占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端）；背景色为黑色 -->
    <div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-black">
      左边栏
    </div>
    <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
      右边栏
    </div>
  </div>
</template>
```

效果如下：

![](images/0.png)

### 1.2、右边栏登录表单

安装图标依赖：

```cmd
pnpm i @element-plus/icons-vue
```

在`src/utils`下新建注册图标的工具类`iconsIntsaller.js`：

```js
// 导入 Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 引入图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
```

在`main.js`中引入并注册：

```js
import { createApp } from 'vue'
import { installIcons } from "@/utils/iconsInstaller"

import router from "./router/index"
import App from './App.vue'

import "@/assets/styles/main.css"

const app = createApp(App)

installIcons(app)
app.use(router)
app.mount('#app')
```

开发登录表单：

```vue
<script setup>
import { User, Lock } from "@element-plus/icons-vue"
</script>

<template>
  <div class="grid grid-cols-2 h-screen">
    <div class="col-span-2 order-2 md:col-span-1 md:order-1 bg-black">
      <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
      <div class="flex justify-center items-center h-full">
        左边栏
      </div>
    </div>
    <div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
      <div class="flex justify-center items-center h-full flex-col">
        <!-- 大标题，设置字体粗细、大小、下边距 -->
        <h1 class="font-bold text-4xl mb-5">欢迎回来</h1>
        <!-- 设置 flex 布局，内容垂直水平居中，文字颜色，以及子内容水平方向 x 轴间距 -->
        <div class="flex items-center justify-center mb-7 text-gray-400 space-x-2">
          <!-- 左边横线，高度为 1px, 宽度为 16，背景色设置 -->
          <span class="h-[1px] w-16 bg-gray-200"></span>
          <span>账号密码登录</span>
          <!-- 右边横线 -->
          <span class="h-[1px] w-16 bg-gray-200"></span>
        </div>
        <!-- 引入 Element Plus 表单组件，移动端设置宽度为 5/6，PC 端设置为 2/5 -->
        <el-form class="w-5/6 md:w-2/5">
          <el-form-item>
            <!-- 输入框组件 -->
            <el-input size="large" placeholder="请输入用户名" :prefix-icon="User" clearable/>
          </el-form-item>
          <el-form-item>
            <!-- 密码框组件 -->
            <el-input size="large" type="password" placeholder="请输入密码" :prefix-icon="Lock" clearable/>
          </el-form-item>
          <el-form-item>
            <!-- 登录按钮，宽度设置为 100% -->
            <el-button class="w-full" size="large" type="primary">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
```

在`main.css`中解决样式冲突问题：
```css
[type='text']:focus, [type='email']:focus, [type='url']:focus, [type='password']:focus, [type='number']:focus, [type='date']:focus, [type='datetime-local']:focus, [type='month']:focus, [type='search']:focus, [type='tel']:focus, [type='time']:focus, [type='week']:focus, [multiple]:focus, textarea:focus, select:focus {
  box-shadow: 0 0 0 1px transparent inset!important;
}
```

最终效果如下：

![](images/1.png)

### 1.3、左边栏效果开发

```html
<!-- 默认占两列，order 用于指定排列顺序，md 用于适配非移动端（PC 端） -->
<div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-slate-900">
  <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
  <div class="flex justify-center items-center h-full flex-col">
    <h2 class="font-bold text-4xl mb-7 text-white">Weblog 博客登录</h2>
    <p class="text-white">一款由 Spring Boot + Mybaits Plus + Vue 3.2 + Vite 4 开发的前后端分离博客。</p>
    <!-- 指定图片宽度为父级元素的 1/2 -->
    <img src="@/assets/images/developer.png" class="w-1/2" alt="developer" />
  </div>
</div>
```

最终效果如下：

![](images/2.png)

### 1.4、添加动画效果

安装依赖：

```cmd
pnpm install animate.css
```

在 main.js 文件中引入它：

```js
import "animate.css"
```

给右边栏的父级 div 添加 bounceInRight 动画：

```html
<div class="col-span-2 order-1 md:col-span-1 md:order-2 bg-white">
  <div class="flex justify-center items-center h-full flex-col animate__animated animate__bounceInRight animate__fast">
    <!-- 省略 -->
  </div>
</div>
```

给左边栏的父级 div 添加 bounceInLeft 动画：

```html
<div class="col-span-2 order-2 p-10 md:col-span-1 md:order-1 bg-slate-900">
  <!-- 指定为 flex 布局，并设置为屏幕垂直水平居中，高度为 100% -->
  <div class="flex justify-center items-center h-full flex-col animate__animateanimate__bounceInLeft animate__fast">
    <!-- 省略 -->
  </div>
</div>
```