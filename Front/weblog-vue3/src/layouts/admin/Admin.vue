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