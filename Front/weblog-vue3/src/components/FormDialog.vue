<script setup>
import { ref } from "vue"

defineProps({
  title: String,
  width: {
    type: String,
    default: "40%"
  },
  destroyOnClose: {
    type: Boolean,
    default: false
  },
  confirmText: {
    type: String,
    default: "提交"
  }
})

const dialogVisible = ref(false)

function open() {
  dialogVisible.value = true
}

function close() {
  dialogVisible.value = false
  isLoading.value = false
}

const isLoading = ref(false)

function switchLoading() {
  isLoading.value = !isLoading.value
}

defineExpose({
  open,
  close,
  switchLoading
})

const emit = defineEmits(["submit"])

function submit() {
  emit("submit")
}
</script>

<template>
  <el-dialog
      v-model="dialogVisible"
      :title="title"
      :width="width"
      :destroy-on-close="destroyOnClose"
  >
    <!-- 插槽 -->
    <slot></slot>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit" :loading="isLoading">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>