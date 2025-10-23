<script setup>
import { useMenuStore } from "@/stores/menu"
import { MENUS } from "@/constants/menus"

const menuStore = useMenuStore()
</script>

<template>
  <div class="fixed top-[64px] h-[44px] px-2 right-0 z-50 flex items-center bg-white transition-all" :style="{ left: menuStore.isMenuCollapsed ? '64px' : '250px' }">
    <!-- 左侧：标签导航 -->
    <el-tabs type="card" closable style="min-width: 10px;">
      <el-tab-pane  key="1" label="Tab 1" name="1">
      </el-tab-pane>
      <el-tab-pane key="2" label="Tab 2" name="2">
      </el-tab-pane>
    </el-tabs>

    <!-- 右侧：下拉菜单 -->
    <span class="ml-auto flex items-center justify-center h-[32px] w-[32px]">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>关闭其他</el-dropdown-item>
            <el-dropdown-item>关闭全部</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </span>
  </div>
  <div class="h-[44px]"></div>
</template>

<style scoped>
:deep(.el-tabs__item) {
  font-size: 12px;
  border: 1px solid #d8dce5!important;
  border-radius: 3px!important;
}

:deep(.el-tabs--card>.el-tabs__header .el-tabs__item) {
  margin-left: 0.1rem!important;
  margin-right: 0.1rem!important;
}

:deep(.el-tabs__item.is-active) {
  background-color: var(--el-color-primary) !important;
  color: #fff;
}

:deep(.el-tabs__item.is-active::before) {
  content: "";
  background-color: #fff;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  position: relative;
  margin-right: 4px;
}

:deep(.el-tabs) {
  height: 32px;
}

:deep(.el-tabs__header) {
  margin-bottom: 0;
}

:deep(.el-tabs--card>.el-tabs__header .el-tabs__nav) {
  border: 0;
}

:deep(.el-tabs--card>.el-tabs__header .el-tabs__item) {
  height: 32px;
  line-height: 32px;
  border: 0;
  background: #fff;
}

:deep(.el-tabs--card>.el-tabs__header) {
  border: 0;
}

:deep(.el-tabs__nav-prev, .el-tabs__nav-next) {
  line-height: 35px;
}

:deep(.is-disabled) {
  cursor: not-allowed;
  color: #d1d5db;
}
</style>