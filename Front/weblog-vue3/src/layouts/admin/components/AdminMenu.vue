<script setup>
import { useMenuStore } from "@/stores/menu"
import { computed } from "vue"
import { MENUS } from "@/constants/menus"
import {useRoute, useRouter} from "vue-router"

const route = useRoute()
const router = useRouter()

const menuStore = useMenuStore()

const isCollapsed = computed(() => menuStore.isMenuCollapsed)
</script>

<template>
  <div class="bg-slate-800 h-screen text-white transition-all">
    <!-- 顶部 Logo, 指定高度为 64px, 和右边的 Header 头保持一样高 -->
    <div class="flex items-center justify-center h-[64px]">
      <img v-if="!isCollapsed" class="h-[70px]" src="@/assets/images/Logo.png" alt="">
      <img v-else class="h-[70px]" src="@/assets/images/Logo_mini.png" alt="">
    </div>

    <!-- 下方菜单 -->
    <el-menu
        class="el-menu-vertical-demo"
        :collapse="isCollapsed"
        :collapse-transition="false"
        :default-active="route.path"
        @select="p => router.push(p)"
    >
      <el-menu-item v-for="(item, index) in MENUS" :key="index" :index="item.path">
        <el-icon>
          <component :is="item.icon" />
        </el-icon>
        <span>{{ item.name }}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<style scoped>
.el-menu {
  background-color: rgb(30 41 59 / 1);
  border-right: 0;
}
.el-menu-item.is-active {
  color: var(--el-color-primary);
}
.el-menu-item.is-active:hover {
  background-color: rgb(30 41 59 / 1);
}
.el-menu-item {
  color: #fff;
}
.el-menu-item:hover {
  background-color: #ffffff10;
}
</style>