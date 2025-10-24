<script setup>
import AdminHeader from "@/layouts/admin/components/AdminHeader.vue"
import AdminMenu from "@/layouts/admin/components/AdminMenu.vue"
import AdminTagsList from "@/layouts/admin/components/AdminTagsList.vue"
import AdminFooter from "@/layouts/admin/components/AdminFooter.vue"

import { useMenuStore } from "@/stores/menu"
const menuStore = useMenuStore()
</script>

<template>
  <!-- 外层容器 -->
  <el-container>

    <!-- 左边侧边栏 -->
    <el-aside class="transition-all" :width="menuStore.isMenuCollapsed ? '64px' : '250px'">
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
        <router-view v-slot="{ Component }">
          <Transition name="fade">
            <KeepAlive :max="10">
              <component :is="Component" />
            </KeepAlive>
          </Transition>
        </router-view>
      </el-main>

      <!-- 底栏容器 -->
      <el-footer>
        <AdminFooter />
      </el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-header, .el-footer {
  padding: 0 !important;
}
/* 内容区域过渡动画：淡入淡出效果 */
/* 刚开始进入时 */
.fade-enter-from {
  /* 透明度 */
  opacity: 0;
}

/* 刚开始结束 */
.fade-enter-to {
  opacity: 1;
}

/* 刚开始离开 */
.fade-leave-from {
  opacity: 1;
}

/* 离开已结束 */
.fade-leave-to {
  opacity: 0;
}

/* 离开进行中 */
.fade-leave-active {
  transition: all 0.3s;
}

/* 进入进行中 */
.fade-enter-active {
  transition: all 0.3s;
  transition-delay: 0.3s;
}
</style>