<script setup>
import { useMenuStore } from "@/stores/menu"
import { ref } from "vue"
import { useRoute, onBeforeRouteUpdate, useRouter } from "vue-router"
import { getTabList, setTabList } from "@/utils/cookie.js"

const menuStore = useMenuStore()

const route = useRoute()
const router = useRouter()

const activeTab = ref(route.path)

const tabList = ref([
  {
    name: "仪表盘",
    path: "/admin/index"
  }
])

function addTab(tab) {
  let isTabExisted = tabList.value.find(item => item.path === tab.path)
  if (!isTabExisted) {
    tabList.value.push(tab)
  }
  setTabList(tabList.value)
}

onBeforeRouteUpdate((to, from) => {
  activeTab.value = to.path
  addTab({
    name: to.meta.title,
    path: to.path
  })
})

function handleTabChange(path) {
  activeTab.value = path
  router.push(path)
}

function initTabList() {
  let tabs = getTabList()
  if (tabs) {
    tabList.value = tabs
  }
}

initTabList()

function handleTabRemove(path) {
  let tabs = tabList.value
  let actTab = activeTab.value

  let tab

  if (actTab === path) {
    for (let i = 0; i < tabs.length; i++) {
      tab = tabs[i]
      if (tab.path === path) {
        let nextTab = tabs[i + 1] || tabs[i - 1]
        if(nextTab) {
          actTab = nextTab.path
        }
      }
    }
  }

  activeTab.value = actTab

  tabList.value = tabList.value.filter(t => t.path !== path)

  setTabList(tabList.value)

  handleTabChange(activeTab.value)
}

function handleCloseTab(command) {
  let indexPath = "/admin/index"
  if (command === "others") {
    tabList.value = tabList.value.filter(t => t.path === indexPath || t.path === activeTab.value)
  }

  if (command === "all") {
    activeTab.value = indexPath
    tabList.value = tabList.value.filter(t => t.path === indexPath)
    handleTabChange(activeTab.value)
  }

  setTabList(tabList.value)
}
</script>

<template>
  <div class="fixed top-[64px] h-[44px] px-2 right-0 z-50 flex items-center bg-white transition-all" :style="{ left: menuStore.isMenuCollapsed ? '64px' : '250px' }">
    <!-- 左侧：标签导航 -->
    <el-tabs v-model="activeTab" @tab-change="handleTabChange" @tab-remove="handleTabRemove" type="card" closable style="min-width: 10px;">
      <el-tab-pane
        v-for="(m, i) in tabList"
        :key="m.path"
        :label="m.name"
        :name="m.path"
        :closable="i > 0"
      />
    </el-tabs>

    <!-- 右侧：下拉菜单 -->
    <span class="ml-auto flex items-center justify-center h-[32px] w-[32px]">
      <el-dropdown @command="handleCloseTab">
        <span class="el-dropdown-link">
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="others">关闭其他</el-dropdown-item>
            <el-dropdown-item command="all">关闭全部</el-dropdown-item>
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