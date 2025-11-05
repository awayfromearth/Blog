<script setup>
import AvatarImg from "@/assets/images/avatar.jpg"

import { useMenuStore } from "@/stores/menu"
import { useUserStore } from "@/stores/user.js"
import { useRouter } from "vue-router"
import { useFullscreen } from "@vueuse/core"
import { ref, reactive, watch } from "vue"
import { updatePassword } from "@/api/admin/user"
import { showMessage } from "@/utils/message"
import { showModel } from "@/utils/model"
import FormDialog from "@/components/FormDialog.vue"

const menuStore = useMenuStore()
const userStore = useUserStore()
const router = useRouter()
const { isFullscreen, toggle } = useFullscreen()

function handleRefresh() {
  location.reload()
}

const formDialogRef = ref(null)
const formRef = ref(null)
const form = reactive({
  username: userStore.userInfo.username || '',
  password: '',
  rePassword: ''
})
const rules = {
  username: [
    {
      required: true,
      message: '用户名不能为空',
      trigger: 'blur'
    }
  ],
  password: [
    {
      required: true,
      message: '密码不能为空',
      trigger: 'blur',
    },
  ],
  rePassword: [
    {
      required: true,
      message: '确认密码不能为空',
      trigger: 'blur',
    },
  ]
}
watch(() => userStore.userInfo.username, v => { form.username = v })
function handleDropdownCommand(command) {
  if (command === "updatePassword") {
    formDialogRef.value.open()
  }

  if (command === "logout") {
    showModel("是否确认要退出登录？").then(() => {
      userStore.logout()
      showMessage("退出登录成功！")
      router.push("/login")
    })
  }
}

function onSubmit() {
  formRef.value.validate(async valid => {
    if (valid) {
      if (form.password !== form.rePassword) {
        return showMessage("两次密码输入不一致，请检查！", "warning")
      }

      formDialogRef.value.switchLoading()
      try {
        const { success, message } = await updatePassword(form)
        if (success) {
          showMessage("密码重置成功，请重新登录！")

          userStore.logout()

          formDialogRef.value.close()
          router.push('/login')
        } else {
          showMessage(message, "error")
        }
      } catch(e) {
        console.log(e)
      } finally {
        formDialogRef.value.switchLoading()
      }
    }
  })
}
</script>

<template>
  <!-- 通过 flex 指定水平布局 -->
  <!-- 设置背景色为白色、高度为 64px，padding-right 为 4， border-bottom 为 slate 200 -->
  <div class="bg-white h-[64px] flex pr-4 border-b border-slate-100">
    <!-- 左边栏收缩、展开 -->
    <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200" @click="menuStore.toggleMenuCollapsed">
      <el-icon>
        <Fold v-if="!menuStore.isMenuCollapsed" />
        <Expand v-else />
      </el-icon>
    </div>

    <!-- 右边容器，通过 ml-auto 让其在父容器的右边 -->
    <div class="ml-auto flex">
      <!-- 点击刷新页面 -->
      <el-tooltip class="box-item" effect="dark" content="刷新" placement="bottom">
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 hover:bg-gray-200" @click="handleRefresh">
          <el-icon>
            <Refresh />
          </el-icon>
        </div>
      </el-tooltip>
      <!-- 点击全屏展示 -->
      <el-tooltip class="box-item" effect="dark" :content="isFullscreen ? '取消全屏' : '全屏'" placement="bottom">
        <div class="w-[42px] h-[64px] cursor-pointer flex items-center justify-center text-gray-700 mr-2 hover:bg-gray-200" @click="toggle">
          <el-icon>
            <FullScreen v-if="!isFullscreen"/>
            <Aim v-else/>
          </el-icon>
        </div>
      </el-tooltip>

      <!-- 登录用户头像 -->
      <el-dropdown trigger="click" class="flex items-center justify-center" @command="handleDropdownCommand">
        <span class="el-dropdown-link flex items-center justify-center text-gray-700 text-xs">
          <!-- 头像 Avatar -->
          <el-avatar class="mr-2" :size="25" :src="AvatarImg" />
          {{ userStore.userInfo.username }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

  <FormDialog ref="formDialogRef" title="修改密码" width="40%" @submit="onSubmit">
    <el-form ref="formRef" :rules="rules" :model="form">
      <el-form-item label="用户名" prop="username" label-width="120px">
        <!-- 输入框组件 -->
        <el-input size="large" v-model="form.username" placeholder="请输入用户名" clearable disabled />
      </el-form-item>
      <el-form-item label="密码" prop="password" label-width="120px">
        <el-input size="large" type="password" v-model="form.password" placeholder="请输入密码" clearable show-password />
      </el-form-item>
      <el-form-item label="确认密码" prop="rePassword" label-width="120px">
        <el-input size="large" type="password" v-model="form.rePassword" placeholder="请确认密码" clearable show-password />
      </el-form-item>
    </el-form>
  </FormDialog>
</template>

<style scoped>
.el-dropdown-link {
  outline: none;
}
:deep(.el-input.is-disabled .el-input__inner) {
  background-color: transparent;
}
</style>
