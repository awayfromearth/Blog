<script setup>
import zhCn from "element-plus/dist/locale/zh-cn"

const locale = zhCn
</script>

<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<style>
#nprogress .bar {
  background: #409eff !important;
}
</style>