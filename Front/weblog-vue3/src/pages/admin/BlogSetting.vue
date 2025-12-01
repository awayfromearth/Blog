<script setup>
import { reactive, ref } from "vue"
import { Check, Close } from "@element-plus/icons-vue"

const formRef = ref()
const form = reactive({
  name: "",
  author: "",
  logo: "",
  avatar: "",
  introduction: "",
  githubHomePage: "",
  giteeHomepage: "",
  zhihuHomepage: "",
  csdnHomepage: "",
})
const rules = {
  name: [
    {
      required: true,
      message: "请输入博客名称",
      trigger: "blur"
    }
  ],
  author: [
    {
      required: true,
      message: "请输入作则名称",
      trigger: "blur"
    }
  ],
  logo: [
    {
      required: true,
      message: "请上传博客 LOGO",
      trigger: "blur"
    }
  ],
  avatar: [
    {
      required: true,
      message: "请上传作者头像",
      trigger: "blur"
    }
  ],
  introduction: [
    {
      required: true,
      message: "请输入介绍语",
      trigger: "blur"
    }
  ]
}
const isGithubChecked = ref(false)
const isGiteeChecked = ref(false)
const isZhihuChecked = ref(false)
const isCSDNChecked = ref(false)
</script>

<template>
  <el-card>
    <el-form ref="formRef" :model="form" label-width="150px" :rules="rules">
      <el-form-item label="博客名称" prop="name">
        <el-input v-model="form.name" clearable />
      </el-form-item>
      <el-form-item label="作者名" prop="author">
        <el-input v-model="form.author" clearable />
      </el-form-item>
      <el-form-item label="博客 LOGO" prop="logo">
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
        >
          <el-icon class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="作者头像" prop="avatar">
        <el-upload
            class="avatar-uploader"
            :show-file-list="false"
        >
          <el-icon class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="介绍语" prop="introduction">
        <el-input v-model="form.introduction" type="textarea" />
      </el-form-item>
      <el-form-item label="开启 GihHub 访问">
        <el-switch v-model="isGithubChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.githubHomepage = ''" />
      </el-form-item>
      <el-form-item label="GitHub 主页访问地址" v-if="isGithubChecked">
        <el-input v-model="form.githubHomepage" clearable placeholder="请输入 GitHub 主页访问的 URL" />
      </el-form-item>
      <el-form-item label="开启 Gitee 访问">
        <el-switch v-model="isGiteeChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.giteeHomepage = ''" />
      </el-form-item>
      <el-form-item label="Gitee 主页访问地址" v-if="isGiteeChecked">
        <el-input v-model="form.giteeHomepage" clearable placeholder="请输入 Gitee 主页访问的 URL" />
      </el-form-item>
      <el-form-item label="开启知乎访问">
        <el-switch v-model="isZhihuChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.zhihuHomepage = ''" />
      </el-form-item>
      <el-form-item label="知乎主页访问地址" v-if="isZhihuChecked">
        <el-input v-model="form.zhihuHomepage" clearable placeholder="请输入知乎主页访问的 URL" />
      </el-form-item>
      <el-form-item label="开启 CSDN 访问">
        <el-switch v-model="isCSDNChecked" inline-prompt :active-icon="Check" :inactive-icon="Close" @change="form.csdnHomepage = ''" />
      </el-form-item>
      <el-form-item label="CSDN 主页访问地址" v-if="isCSDNChecked">
        <el-input v-model="form.csdnHomepage" clearable placeholder="请输入 CSDN 主页访问的 URL" />
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style scoped>
.el-card {
  width: 100%;
  max-height: calc(100vh - 148px);
  overflow: auto;
}

:deep(.avatar-uploader .avatar) {
  width: 100px;
  height: 100px;
  display: block;
}

:deep(.avatar-uploader .el-upload) {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

:deep(.avatar-uploader .el-upload:hover) {
  border-color: var(--el-color-primary);
}

:deep(.el-icon.avatar-uploader-icon) {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  text-align: center;
}
</style>